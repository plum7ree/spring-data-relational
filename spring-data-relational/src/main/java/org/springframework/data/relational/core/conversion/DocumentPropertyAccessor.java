/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.relational.core.conversion;

import java.util.Map;

import org.springframework.context.expression.MapAccessor;
import org.springframework.data.relational.domain.RowDocument;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.TypedValue;
import org.springframework.lang.Nullable;

/**
 * {@link org.springframework.expression.PropertyAccessor} to allow entity based field access to
 * {@link org.springframework.data.relational.domain.RowDocument}s.
 *
 * @author Mark Paluch
 */
class DocumentPropertyAccessor extends MapAccessor {

	static final MapAccessor INSTANCE = new DocumentPropertyAccessor();

	@Override
	public Class<?>[] getSpecificTargetClasses() {
		return new Class[] { RowDocument.class };
	}

	@Override
	public boolean canRead(EvaluationContext context, @Nullable Object target, String name) {
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public TypedValue read(EvaluationContext context, @Nullable Object target, String name) {

		if (target == null) {
			return TypedValue.NULL;
		}

		Map<String, Object> source = (Map<String, Object>) target;

		Object value = source.get(name);
		return value == null ? TypedValue.NULL : new TypedValue(value);
	}
}
