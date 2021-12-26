package org.training.campus.onlineshop.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.training.campus.onlineshop.dao.DataAccessException;
import org.training.campus.onlineshop.dao.ProductDao;
import org.training.campus.onlineshop.entity.Product;

public class JdbcProductDao implements ProductDao {

	private static final String SCHEMA = "\"onlineshop\"";
	private static final String TABLE = "product";
	
	private static final String URL_KEY = "url";
	private static final String USER_KEY = "user";
	private static final String PASSWORD_KEY = "password";

	protected static final String FETCH_ALL_STATEMENT = String
			.format("select id, name, price, creation_date from %s.%s order by name asc", SCHEMA, TABLE);
	protected static final String INSERT_ENTITY_STATEMENT = String
			.format("insert into %s.%s (name, price, creation_date) values(?,?,?)", SCHEMA, TABLE);
	protected static final String UPDATE_ENTITY_STATEMENT = String
			.format("update %s.%s set name=?, price=?, creation_date=? where id=?", SCHEMA, TABLE);
	protected static final String DELETE_ENTITY_STATEMENT = String.format("delete from %s.%s where id=?", SCHEMA,
			TABLE);

	private final Properties props;
	private final ProductRowMapper mapper;

	public JdbcProductDao(String url, String user, String password) {
		mapper = new ProductRowMapper();
		props = new Properties();
		props.put(URL_KEY, Objects.requireNonNull(url, "jdbc url to DB shouldn't be null"));
		props.put(USER_KEY, user);
		props.put(PASSWORD_KEY, password);
	}

	public List<Product> getAll() {
		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery(FETCH_ALL_STATEMENT)) {

			List<Product> products = new ArrayList<>();
			while (resultSet.next()) {
				products.add(mapper.mapRowToProduct(resultSet));
			}
			return products;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	public void persist(Product product) {
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(INSERT_ENTITY_STATEMENT,
						Statement.RETURN_GENERATED_KEYS)) {

			mapper.fillInStatementValues(stmt, product);
			conn.setAutoCommit(false);
			stmt.executeUpdate();
			try (ResultSet keySet = stmt.getGeneratedKeys()) {
				if (keySet.next()) {
					product.setId(keySet.getLong("id"));
					conn.commit();
				} else {
					throw new DataAccessException("no primary key for new tuple");
				}
			} finally {
				try {
					conn.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	public void merge(Product product) {
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(UPDATE_ENTITY_STATEMENT)) {

			final int index = mapper.fillInStatementValues(stmt, product);
			stmt.setLong(index, product.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	public void remove(Product product) {
		remove(product.getId());
	}

	public void remove(long id) {
		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(DELETE_ENTITY_STATEMENT)) {

			stmt.setLong(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	public ProductRowMapper getMapper() {
		return mapper;
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(props.getProperty(URL_KEY), props);
	}
}
