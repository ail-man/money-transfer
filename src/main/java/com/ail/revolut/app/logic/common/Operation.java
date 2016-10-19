package com.ail.revolut.app.logic.common;

import javax.persistence.EntityManager;

public interface Operation<T> {

	T perform(EntityManager em) throws Exception;

}
