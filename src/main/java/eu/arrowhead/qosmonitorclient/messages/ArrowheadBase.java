/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.arrowhead.qosmonitorclient.messages;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class ArrowheadBase {

  //Collection and Map field names will get added only if they are NOT empty!
  protected Set<String> getFieldNamesWithNonNullValue() {
    Class aClass = getClass();
    Field[] fields = aClass.getDeclaredFields();
    Set<String> lines = new HashSet<>(fields.length);

    Arrays.stream(fields).forEach(field -> {
      field.setAccessible(true);
      try {
        Object o = field.get(this);
        if (o != null) {
          if (o instanceof Collection<?>) {
            if (!((Collection) o).isEmpty()) {
              lines.add(field.getName());
            }
          } else if (o instanceof Map<?, ?>) {
            if (!((Map) o).isEmpty()) {
              lines.add(field.getName());
            }
          } else {
            lines.add(field.getName());
          }
        }
      } catch (final IllegalAccessException e) {
        throw new AssertionError(field.toString() + " field is not accessible!");
      }
    });

    return lines;
  }

  protected Set<String> prefixFieldNames(Set<String> nonNullFields) {
    String prefix = getClass().getSimpleName();
    if (nonNullFields.contains("address")) {
      nonNullFields.remove("address");
      nonNullFields.add(prefix + ":address");
    }
    if (nonNullFields.contains("port")) {
      nonNullFields.remove("port");
      nonNullFields.add(prefix + ":port");
    }
    if (nonNullFields.contains("authenticationInfo")) {
      nonNullFields.remove("authenticationInfo");
      nonNullFields.add(prefix + ":authenticationInfo");
    }
    return nonNullFields;
  }

}

