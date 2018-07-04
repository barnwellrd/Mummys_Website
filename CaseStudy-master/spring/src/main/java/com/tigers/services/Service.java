package com.tigers.services;

import java.util.List;

public interface Service<E> {
	public void delete(String id);
	public void add(E obj);
	public void update(E obj);
	public E get(String id);
	public List<E> list();
}
