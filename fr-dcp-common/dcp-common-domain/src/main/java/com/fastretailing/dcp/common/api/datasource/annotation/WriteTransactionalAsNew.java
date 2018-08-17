/**
 * @(#)WriteTransactionalAsNew.java
 *
 * Copyright (c) 2017 Fast Retailing Corporation.
 */
package com.fastretailing.dcp.common.api.datasource.annotation;

import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * WriteTransactionalAsNew.<br>
 *     <p>
 *         Set the propagation of Transactional to Propagation.REQUIRES_NEW.
 *         This annotation will take effect before {@link org.springframework.jdbc.datasource.DataSourceTransactionManager}
 *         create transaction.So, it will use the current datasource(switched by the annotation) to create transaction.
 *     </p>
 *
 * @author Fast Retailing
 * @version $Revision$
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Order(1)
public @interface WriteTransactionalAsNew {

    /**
     * Datasource's name<br>
     */
    String name() default "";

}
