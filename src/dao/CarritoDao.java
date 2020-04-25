package dao;
import modelo.Carrito;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class CarritoDao {


		private static Session session;
		private Transaction tx;

		private void iniciarOperacion() throws HibernateException {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();
		}

		private void manejaExcepcion(HibernateException he) throws HibernateException {
			tx.rollback();
			throw new HibernateException("ERROR en la capa de acceso de datos", he);
		}


		public int agregar(Carrito objeto) {
			int id = 0;

			try {
				iniciarOperacion();
				id = Integer.parseInt(session.save(objeto).toString());
				tx.commit();
			} catch (HibernateException he) {
				manejaExcepcion(he);
				throw he;
			} finally {
				session.close();
			}
			return id;

		}

		public void actualizar(Carrito objeto) throws HibernateException {
			try {
				iniciarOperacion();
				session.update(objeto);
				tx.commit();
			} catch (HibernateException he) {
				manejaExcepcion(he);
				throw he;
			} finally {
				session.close();
			}
		}

		public void Eliminar(Carrito objeto) throws HibernateException {
			try {
				iniciarOperacion();
				session.delete(objeto);
				tx.commit();
			} catch (HibernateException he) {
				manejaExcepcion(he);
				throw he;
			} finally {
				session.close();
			}
		}
	
	
		public Carrito traer(int idCarrito) throws HibernateException {
			Carrito objeto = null;

			try {
				iniciarOperacion();
				objeto = (Carrito) session.get(Carrito.class, idCarrito);
			} finally {
				session.close();
			}

			return objeto;
		}
	
	
	
	
	
	
	
}//end