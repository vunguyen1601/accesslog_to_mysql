/**
 * This file is part of the test project
 * Id: xyz.vtools/HelpCoreDB.java
 * Location: VHES
 * Developer: Nguyen Thach Vu
 * Date: May 25, 2016 8:37:24 AM
 * Email: nguyenthachvu.vn@gmail.com
 * Skype: vunguyen_1601
 * Note: N/A
 */
package xyz.vtools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;

// TODO: Auto-generated Javadoc
/**
 * The Class HelpCoreDB.
 */
public abstract class HelpCoreDB {

	// public static String sSchema = "HES.";
	// 607853
	public static Integer iQueryTimeout = 300 * 4; // 300 giây = 5 phút
	// public static Integer iQueryTimeout = 300; //300 giây = 5 phút
	// public static Integer iQueryTimeout = 600; //600 giây = 10 phút

	/** The logger. */
	protected static Logger logger = Logger.getLogger("hes.database");

	/**
	 * Count all.
	 *
	 * @param <T>
	 *            the generic type
	 * @param enClass
	 *            the en class
	 * @return the long
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	public static final <T> long countAll(Class<T> enClass) throws HibernateException, Exception {
		return countByCriteria(enClass);
	}

	/**
	 * Find by id.
	 *
	 * @param <T>
	 *            the generic type
	 * @param enClass
	 *            the en class
	 * @param id
	 *            the id
	 * @return the t
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	public static final <T> T findById(Class<T> enClass, Serializable id) throws HibernateException, Exception {
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		// Open Session
		Session s = sf.openSession();

		// Transaction tx = null;
		T result = null;
		try {
			// tx = s.beginTransaction();

			result = (T) s.get(enClass, id);

			// tx.commit();

		} catch (HibernateException he) {
			// if (tx != null)
			// tx.rollback();
			logger.error(he.getMessage(), he);
			s.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			s.close();
			throw he;
		} finally {
			// Close Session
			s.close();
		}

		return result;
	}

	/**
	 * Find by named params query.
	 *
	 * @param sQuery
	 *            the s query
	 * @param params
	 *            the params
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @return the list
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public static final List<Object[]> findByNamedParamsQuery(String sQuery, Map<String, ? extends Object> params,
			int start, int limit) throws HibernateException, Exception {
		List<Object[]> result = new ArrayList<Object[]>();
		// Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			// tx = session.beginTransaction();

			Query query = session.createQuery(sQuery).setTimeout(iQueryTimeout);
			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}
			if (start > 0) {
				query.setFirstResult(start);
			}

			if (limit > 0) {
				query.setMaxResults(limit);
			}
			result = query.list();
			// tx.commit();

		} catch (HibernateException he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static final <T> List<Map<String, T>> findByListMapQuery2Entity(String sQuery,
			Map<String, ? extends Object> params, int start, int limit) throws HibernateException, Exception {

		List<Map<String, T>> result = new ArrayList<Map<String, T>>();
		// Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			// tx = session.beginTransaction();

			Query query = session.createQuery(sQuery).setTimeout(iQueryTimeout);
			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}

			if (start > 0) {
				query.setFirstResult(start);
			}

			if (limit > 0) {
				query.setMaxResults(limit);
			}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			result = query.list();
			// tx.commit();

		} catch (HibernateException he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static final <T> List<Map<String, T>> findByListMapSQLQuery2Entity(String sQuery,
			Map<String, ? extends Object> params, int start, int limit) throws HibernateException, Exception {

		List<Map<String, T>> result = new ArrayList<Map<String, T>>();
		// Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			// tx = session.beginTransaction();

			Query query = session.createSQLQuery(sQuery).setTimeout(iQueryTimeout);
			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}

			if (start > 0) {
				query.setFirstResult(start);
			}

			if (limit > 0) {
				query.setMaxResults(limit);
			}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			result = query.list();
			// tx.commit();

		} catch (HibernateException he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static final <T> List<T> findByQuery2Entity(String sQuery, Map<String, ? extends Object> params, int start,
			int limit) throws HibernateException, Exception {
		List<T> result = new ArrayList<T>();
		// Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			// tx = session.beginTransaction();

			Query query = session.createQuery(sQuery).setTimeout(iQueryTimeout);
			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}
			if (start > 0) {
				query.setFirstResult(start);
			}

			if (limit > 0) {
				query.setMaxResults(limit);
			}
			result = query.list();
			// tx.commit();

		} catch (HibernateException he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static final <T> List<T> findByQuery2Bean(Class<T> enClass, String sQuery,
			Map<String, ? extends Object> params, int start, int limit) throws HibernateException, Exception {
		List<T> result = new ArrayList<T>();
		// Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			// tx = session.beginTransaction();

			Query query = session.createQuery(sQuery).setTimeout(iQueryTimeout);
			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}
			if (start > 0) {
				query.setFirstResult(start);
			}

			if (limit > 0) {
				query.setMaxResults(limit);
			}

			query.setResultTransformer(Transformers.aliasToBean(enClass));
			// query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			result = query.list();
			// tx.commit();

		} catch (HibernateException he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	/**
	 * Find by map query.
	 *
	 * @param sQuery
	 *            the s query
	 * @param params
	 *            the params
	 * @param start
	 *            the start
	 * @param limit
	 *            the limit
	 * @return the list
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public static final List<Map<String, Object>> findByMapQuery(String sQuery, Map<String, ? extends Object> params,
			int start, int limit) throws HibernateException, Exception {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			// tx = session.beginTransaction();
			Query query = session.createQuery(sQuery).setTimeout(iQueryTimeout);

			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}

			if (start > 0) {
				query.setFirstResult(start);
			}

			if (limit > 0) {
				query.setMaxResults(limit);
			}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			result = query.list();
			// tx.commit();

		} catch (HibernateException he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static final List<Map<String, Object>> findByMapSQLQuery(String sQuery, Map<String, ? extends Object> params,
			int start, int limit) throws HibernateException, Exception {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		// Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			// tx = session.beginTransaction();

			Query query = session.createSQLQuery(sQuery).setTimeout(iQueryTimeout);
			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}

			if (start > 0) {
				query.setFirstResult(start);
			}

			if (limit > 0) {
				query.setMaxResults(limit);
			}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			result = query.list();
			// tx.commit();

		} catch (HibernateException he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	/**
	 * Find by named params sql query.
	 *
	 * @param sQuery
	 *            the s query
	 * @param params
	 *            the params
	 * @return the list
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public static final List<Object[]> findByNamedParamsSQLQuery(String sQuery, Map<String, ? extends Object> params)
			throws HibernateException, Exception {
		List<Object[]> result = new ArrayList<Object[]>();

		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {

			Query query = session.createSQLQuery(sQuery).setTimeout(iQueryTimeout);

			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}

			result = query.list();

		} catch (HibernateException he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	/**
	 * Find by map sql query.
	 *
	 * @param sQuery
	 *            the s query
	 * @param params
	 *            the params
	 * @return the list
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public static final List<Map<String, Object>> findByMapSQLQuery(String sQuery, Map<String, ? extends Object> params)
			throws HibernateException, Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {

			Query query = session.createSQLQuery(sQuery).setTimeout(iQueryTimeout);

			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}
			query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			result = query.list();

		} catch (HibernateException he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	/**
	 * Find by all.
	 *
	 * @param <T>
	 *            the generic type
	 * @param enClass
	 *            the en class
	 * @param order
	 *            the order
	 * @param criterion
	 *            the criterion
	 * @return the list
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	public static final <T> List<T> findByAll(Class<T> enClass, Order[] order, Criterion... criterion)
			throws HibernateException, Exception {
		return findByCriteria(enClass, -1, -1, order, criterion);
	}

	/**
	 * Find by criteria.
	 *
	 * @param <T>
	 *            the generic type
	 * @param enClass
	 *            the en class
	 * @param firstResult
	 *            the first result
	 * @param maxResults
	 *            the max results
	 * @param order
	 *            the order
	 * @param criterion
	 *            the criterion
	 * @return the list
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public static final <T> List<T> findByCriteria(Class<T> enClass, int firstResult, int maxResults, Order[] order,
			Criterion... criterion) throws HibernateException, Exception {

		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		List<T> result = null;
		try {

			Criteria crit = session.createCriteria(enClass);

			if (criterion != null) {
				for (Criterion c : criterion) {
					crit.add(c);
				}
			}
			if (order != null) {
				for (Order c : order) {
					crit.addOrder(c);
				}
			}
			if (firstResult > 0) {
				crit.setFirstResult(firstResult);
			}

			if (maxResults > 0) {
				crit.setMaxResults(maxResults);
			}

			result = crit.list();

		} catch (HibernateException he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	/**
	 * Count by criteria.
	 *
	 * @param <T>
	 *            the generic type
	 * @param enClass
	 *            the en class
	 * @param criterion
	 *            the criterion
	 * @return the long
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	public static final <T> long countByCriteria(Class<T> enClass, Criterion... criterion)
			throws HibernateException, Exception {

		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		long result = 0;
		try {

			Criteria crit = session.createCriteria(enClass);
			crit.setProjection(Projections.rowCount());

			for (Criterion c : criterion) {
				crit.add(c);
			}

			result = ((Long) crit.uniqueResult()).longValue();

		} catch (HibernateException he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	/**
	 * Aggregate query.
	 *
	 * @param sQuery
	 *            the s query
	 * @return the long
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	public static final long aggregateQuery(String sQuery, Map<String, ? extends Object> params)
			throws HibernateException, Exception {

		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		long result = 0;
		try {

			Query query = session.createQuery(sQuery).setTimeout(iQueryTimeout);

			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}

			result = ((Long) query.uniqueResult()).longValue();

		} catch (HibernateException he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	public static final long aggregateQuery(String sQuery) throws HibernateException, Exception {

		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		long result = 0;
		try {

			Query query = session.createQuery(sQuery).setTimeout(iQueryTimeout);

			result = ((Long) query.uniqueResult()).longValue();

		} catch (HibernateException he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	/**
	 * Delete.
	 *
	 * @param <T>
	 *            the generic type
	 * @param entity
	 *            the entity
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	public static final <T> void delete(T entity) throws HibernateException, Exception {
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			tx = session.beginTransaction();

			session.delete(entity);

			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

	}

	/**
	 * Save.
	 *
	 * @param <T>
	 *            the generic type
	 * @param entity
	 *            the entity
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ict.tca.data.IDB#save(java.lang.Object)
	 */
	public static final <T> void saveList(List<T> entity) throws HibernateException, Exception {
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			tx = session.beginTransaction();

			for (T obj : entity) {
				session.save(obj);
			}

			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}
	}

	public static final <T> void mergeList(List<T> entity) throws HibernateException, Exception {
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			tx = session.beginTransaction();

			for (T obj : entity) {
				session.merge(obj);
			}

			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static final <T> T merge(T entity) throws HibernateException, Exception {
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		T result;

		try {
			tx = session.beginTransaction();

			result = (T) session.merge(entity);

			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	public static final <T> void deleteList(List<T> entity) throws HibernateException, Exception {
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			tx = session.beginTransaction();

			for (T obj : entity) {
				session.delete(obj);
			}

			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}
	}

	/**
	 * Save.
	 *
	 * @param <T>
	 *            the generic type
	 * @param entity
	 *            the entity
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ict.tca.data.IDB#save(java.lang.Object)
	 */
	public static final <T> Serializable save(T entity) throws HibernateException, Exception {
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		Serializable result;
		try {
			tx = session.beginTransaction();

			result = session.save(entity);

			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}
		return result;
	}

	/**
	 * Persist.
	 *
	 * @param <T>
	 *            the generic type
	 * @param entity
	 *            the entity
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	public static final <T> void persist(T entity) throws HibernateException, Exception {
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			tx = session.beginTransaction();

			session.persist(entity);

			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ict.tca.data.IDB#update(java.lang.Object)
	 */

	/**
	 * Update.
	 *
	 * @param <T>
	 *            the generic type
	 * @param entityClass
	 *            the entity class
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	public static final <T> void update(T entityClass) throws HibernateException, Exception {

		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			tx = session.beginTransaction();

			session.update(entityClass);

			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}
	}

	/**
	 * Save or update.
	 *
	 * @param <T>
	 *            the generic type
	 * @param device
	 *            the device
	 * @return the t
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	public static final <T> T saveOrUpdate(T device) throws HibernateException, Exception {
		// Get SessionFactory
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		// Open Session
		Session s = sf.openSession();

		Transaction tx = null;
		try {
			tx = s.beginTransaction();

			s.saveOrUpdate(device);

			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			s.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			s.close();
			throw he;
		} finally {
			// Close Session
			s.close();
		}

		return device;
	}

	/**
	 * Excute query.
	 *
	 * @param sQuery
	 *            the s query
	 * @param params
	 *            the params
	 * @return the int
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	public static final int excuteMultiQuery(List<String> sQuery, Map<String, ? extends Object> params)
			throws HibernateException, Exception {
		// List<Object[]> result = new ArrayList<Object[]>();
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		int result = 0;

		try {
			tx = session.beginTransaction();

			for (String squery : sQuery) {
				// System.err.println("Query: "+squery);
				Query query = session.createQuery(squery).setTimeout(iQueryTimeout);
				if (params != null) {
					for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
						query.setParameter(param.getKey(), param.getValue());
					}
				}
				result = query.executeUpdate();
			}

			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	/**
	 * Excute query.
	 *
	 * @param sQuery
	 *            the s query
	 * @param params
	 *            the params
	 * @return the int
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	public static final int excuteQuery(String sQuery, Map<String, ? extends Object> params)
			throws HibernateException, Exception {
		// List<Object[]> result = new ArrayList<Object[]>();
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		int result = 0;

		try {
			tx = session.beginTransaction();

			Query query = session.createQuery(sQuery).setTimeout(iQueryTimeout);
			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}

			result = query.executeUpdate();
			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	public static final int excuteSQLQuery(String sQuery, Map<String, ? extends Object> params)
			throws HibernateException, Exception {
		// List<Object[]> result = new ArrayList<Object[]>();
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		int result = 0;

		try {
			tx = session.beginTransaction();

			Query query = session.createSQLQuery(sQuery).setTimeout(iQueryTimeout);
			if (params != null) {
				for (Map.Entry<String, ? extends Object> param : params.entrySet()) {
					query.setParameter(param.getKey(), param.getValue());
				}
			}

			result = query.executeUpdate();
			tx.commit();

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {
			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	/**
	 * Find by paras.
	 *
	 * @param <T>
	 *            the generic type
	 * @param enClass
	 *            the en class
	 * @param criterion
	 *            the criterion
	 * @return the t
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */

	public static final <T> T findDataByField(Class<T> enClass, String field, Object value)
			throws HibernateException, Exception {
		// TODO Auto-generated method stub
		T result = null;
		try {
			result = findByParas(enClass, Restrictions.eq(field, value));

		} catch (HibernateException he) {

			logger.error(he.getMessage(), he);
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			throw he;
		} finally {
			// Close Session
		}

		return result;
	}

	/**
	 * Find by paras.
	 *
	 * @param <T>
	 *            the generic type
	 * @param enClass
	 *            the en class
	 * @param criterion
	 *            the criterion
	 * @return the t
	 * @throws HibernateException
	 *             the hibernate exception
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T findByParas(Class<T> enClass, Criterion... criterion)
			throws HibernateException, Exception {
		// TODO Auto-generated method stub

		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		T result = null;
		try {

			Criteria crit = session.createCriteria(enClass);
			// crit.setProjection(Projections.rowCount());

			for (Criterion c : criterion) {
				crit.add(c);
			}

			result = ((T) crit.uniqueResult());

		} catch (HibernateException he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} catch (Exception he) {

			logger.error(he.getMessage(), he);
			session.close();
			throw he;
		} finally {
			// Close Session
			session.close();
		}

		return result;
	}

	/**
	 * Update.
	 * 
	 * @Note: không dùng cho tr hợp field tìm kiếm và field update trùng nhau
	 * @update: Mr Danglph
	 *
	 * @param sTable
	 *            the s table
	 * @param sField_Data
	 *            the s field data
	 * @param sCondition
	 *            the s condition
	 * @return true, if successful
	 */
	public static boolean update(String sTable, Map<String, Object> sField_Data, Map<String, Object> sCondition) {
		boolean result = false;
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			tx = session.beginTransaction();
			String sQuery_UpdateDCU = "";

			for (Entry<String, Object> entry : sField_Data.entrySet()) {
				if (!sQuery_UpdateDCU.equals(""))
					sQuery_UpdateDCU += ",";

				sQuery_UpdateDCU += entry.getKey() + "=:" + entry.getKey();

			}

			String sQuery_Condition = "";
			for (Entry<String, Object> entry : sCondition.entrySet()) {
				if (!sQuery_Condition.equals(""))
					sQuery_Condition += " AND ";

				sQuery_Condition += entry.getKey() + "=:" + entry.getKey();

			}

			sQuery_UpdateDCU = "UPDATE " + sTable + " SET " + sQuery_UpdateDCU + " WHERE " + sQuery_Condition;

			Query query = session.createQuery(sQuery_UpdateDCU).setTimeout(iQueryTimeout);

			for (Entry<String, Object> entry : sField_Data.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}

			for (Entry<String, Object> entry : sCondition.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}

			query.executeUpdate();

			tx.commit();

			result = true;

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			// session.close();
			// throw he;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// session.close();
			// throw e;
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Update 2.
	 * 
	 * @author danglph Dùng cho cả trường hợp field tìm kiếm và field update trùng
	 *         nhau
	 *
	 * @param sTable
	 *            the s table
	 * @param sField_Data
	 *            the s field data
	 * @param sCondition
	 *            the s condition
	 * @return true, if successful
	 */
	public static boolean update2(String sTable, Map<String, Object> sField_Data, Map<String, Object> sCondition) {
		boolean result = false;
		Transaction tx = null;
		SessionFactory sf = ConnectSession.getsessionFactoryConfig();
		Session session = sf.openSession();
		try {
			tx = session.beginTransaction();
			String sQuery_UpdateDCU = "";

			for (Entry<String, Object> entry : sField_Data.entrySet()) {
				if (!sQuery_UpdateDCU.equals(""))
					sQuery_UpdateDCU += ",";

				sQuery_UpdateDCU += entry.getKey() + "=:" + entry.getKey();

			}

			String sQuery_Condition = "";
			for (Entry<String, Object> entry : sCondition.entrySet()) {
				if (!sQuery_Condition.equals(""))
					sQuery_Condition += " AND ";

				sQuery_Condition += entry.getKey() + "=:" + entry.getKey() + "Danglph";

			}

			sQuery_UpdateDCU = "UPDATE " + sTable + " SET " + sQuery_UpdateDCU + " WHERE " + sQuery_Condition;

			Query query = session.createQuery(sQuery_UpdateDCU).setTimeout(iQueryTimeout);

			for (Entry<String, Object> entry : sField_Data.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}

			for (Entry<String, Object> entry : sCondition.entrySet()) {
				query.setParameter(entry.getKey() + "Danglph", entry.getValue());
			}

			query.executeUpdate();

			tx.commit();

			result = true;

		} catch (HibernateException he) {
			if (tx != null)
				tx.rollback();
			logger.error(he.getMessage(), he);
			// session.close();
			// throw he;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// session.close();
			// throw e;
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * Find object.
	 * 
	 * @author danglph
	 * @createdate 08/02/2018
	 * 
	 *
	 * @param <T>
	 *            the generic type
	 * @param enClass
	 *            the en class
	 * @param field
	 *            the field
	 * @param value
	 *            the value
	 * @return the t
	 */
	public static final <T> T findObject(Class<T> enClass, String field, Object value) {
		T fisEntity = null;
		try {
			fisEntity = findByParas(enClass, Restrictions.eq(field, value));

		} catch (HibernateException he) {
			// TODO Auto-generated catch block
			logger.error(he.getMessage(), he);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}

		return fisEntity;
	}

}
