package dao;

import model.student;

import java.util.ArrayList;

public interface DAOInterface<T> {

    public int insert(T t);

    public int update(T t);

    public int delete(T t);

    public ArrayList<T> getAll();

    public T getById(T t);

    public ArrayList<T> getByCondition(T t);

    public String getStatusbyId(T t);
}
