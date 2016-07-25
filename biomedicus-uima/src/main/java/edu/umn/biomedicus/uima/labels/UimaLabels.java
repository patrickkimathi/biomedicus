/*
 * Copyright (c) 2016 Regents of the University of Minnesota.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.umn.biomedicus.uima.labels;

import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import edu.umn.biomedicus.common.labels.AbstractLabels;
import edu.umn.biomedicus.common.labels.Label;
import edu.umn.biomedicus.common.labels.Labels;
import edu.umn.biomedicus.common.text.TextLocation;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;

import java.util.Iterator;
import java.util.Map;

public final class UimaLabels<T> extends AbstractLabels<T> {
    private final CAS cas;
    private final LabelAdapter<T> labelAdapter;
    private final Iterable<Label<T>> iterable;
    private Type annotationType;
    private Type type;

    private UimaLabels(CAS cas, LabelAdapter<T> labelAdapter, Iterable<Label<T>> iterable) {
        this.cas = cas;
        this.labelAdapter = labelAdapter;
        this.iterable = iterable;
        type = labelAdapter.getType();
        annotationType = cas.getTypeSystem().getType("uima.tcas.Annotation");
    }

    @Inject
    public UimaLabels(CAS cas, LabelAdapter<T> labelAdapter) {
        this.cas = cas;
        this.labelAdapter = labelAdapter;
        annotationType = cas.getTypeSystem().getType("uima.tcas.Annotation");
        type = labelAdapter.getType();
        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(type);
        iterable = () -> new FSIteratorAdapter<>(annotationIndex, labelAdapter::annotationToLabel);
    }

    @Override
    public Labels<T> insideSpan(TextLocation textLocation) {
        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(type);
        AnnotationFS bound = cas.createAnnotation(annotationType, textLocation.getBegin() - 1,
                textLocation.getEnd() + 1);
        Iterable<Label<T>> iterable = () -> FSIteratorAdapter.coveredIteratorAdapter(annotationIndex, bound,
                labelAdapter::annotationToLabel);
        return new UimaLabels<>(cas, labelAdapter, iterable).filter(textLocation::contains);
    }

    @Override
    public Iterator<Label<T>> iterator() {
        return iterable.iterator();
    }
}
