package ru.itmo.wp.model.repository.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public interface ObjectTransformer<T>{
    T toObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
}