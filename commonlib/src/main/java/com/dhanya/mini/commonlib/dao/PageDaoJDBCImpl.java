package com.dhanya.mini.commonlib.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.dhanya.mini.commonlib.domain.ElementDomain;
import com.dhanya.mini.commonlib.domain.PageDomain;
import com.dhanya.mini.commonlib.exception.DbException;
import com.dhanya.mini.commonlib.exception.ResourceNotFoundException;
/**
 * JDBC implementation of Page DAO.
 * 
 * @author Leela
 */
public class PageDaoJDBCImpl implements PageDao {
	
	private static final Logger log = Logger.getLogger(PageDaoJDBCImpl.class.getName());
	
	private JdbcConnectionPool pool;
	
	private final String CREATE_PAGE = "INSERT INTO page (uniqueId,name) VALUES " + "(?,?)";
	
	private final String SELECT_ALL_PAGES = "SELECT * FROM page ";
	
	private final String GET_ELEMENT_LIST = "SELECT * FROM element WHERE pUniqueId = ?";
	
	private final String SELECT_PAGEID ="SELECT * FROM page WHERE pUniqueId=?";
	
	private final String UPDATE_PAGE = "UPDATE page SET name = ? WHERE uniqueId = ?";
	
	private final String SELECT_PAGE = "SELECT * FROM page WHERE uniqueId = ?";
	
	private final String DELETE_PAGE_WITH_ELEMENT = "DELETE element, page FROM page INNER JOIN element " 
            + "WHERE element.pUniqueId = page.uniqueId AND element.pUniqueId = ?";
	
	private final String INSERT_ELEMENT = "INSERT INTO element(content, xcord, ycord, eUniqueId, pUniqueId, type) VALLUES (?, ?, ?, ?, ?, ?)";
	
	@Autowired
	public PageDaoJDBCImpl(JdbcConnectionPool pool){
		this.pool = pool;
	}


	public PageDomain create(PageDomain pageDomain) {
		String uuid = UUID.randomUUID().toString();
		Connection conn = null;

		try {
			conn = pool.getConnectionFromPool();
			PreparedStatement preparedStatement = conn
					.prepareStatement(CREATE_PAGE);
			preparedStatement.setString(1, uuid);
			preparedStatement.setString(2, pageDomain.getName());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException ex) {
			log.error("DataBase exception thrown", ex);
			throw new DbException("DataBase exception thrown", ex);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException ex) {
					log.error("SQL exception", ex);
					throw new DbException("DataBase exception thrown", ex);
				}
			}

		}
		pageDomain.setUuid(uuid);
		return pageDomain;
	}
	
	public boolean delete(String uniqueId) {
		Connection conn = null;
		
		try {
		
			conn = pool.getConnectionFromPool();		
			
			PreparedStatement preparedStatementOne = conn.prepareStatement(SELECT_PAGE);
			preparedStatementOne.setString(1, uniqueId);
			
			ResultSet resultSet = preparedStatementOne.executeQuery();
			
			if(resultSet != null) {
				PreparedStatement preparedStatement = 
					conn.prepareStatement(DELETE_PAGE_WITH_ELEMENT);
			   preparedStatement.setString(1, uniqueId);
			   preparedStatement.executeUpdate();
			   preparedStatement.close();
			 } 
			
			else{
				 throw new ResourceNotFoundException("No page is present with the requested Id");
			    }
			 preparedStatementOne.close();
			 resultSet.close();
		} catch (SQLException e) {
			log.info(e);
			throw new DbException("DataBase exception thrown",e); 
		}
		return true;
	}
	
	public PageDomain update(String uuid,PageDomain pageDomain) {
			
			Connection conn;
			List<ElementDomain> oldElementDomainlist;
			
			try {
			    conn = pool.getConnectionFromPool();
				
			    PreparedStatement preparedStatementPage = conn.prepareStatement(SELECT_PAGEID);
				preparedStatementPage.setString(1, uuid);
				
				//populating the elementList with uuid before updating it
				oldElementDomainlist = getElementList(conn, uuid);
				
				if(pageDomain.getElementDomainList().size() > 1 && pageDomain.getElementDomainList() != null){
					 PreparedStatement preparedStatementone = null;
					
					 for(int i=0;i<oldElementDomainlist.size();i++){
	                    preparedStatementone = conn.prepareStatement(DELETE_PAGE_WITH_ELEMENT);
	                    preparedStatementone.setString(1,oldElementDomainlist.get(i).getElementUniqueId());
				        preparedStatementone.executeUpdate();
					}
				  preparedStatementone.close();
				}
				  PreparedStatement preparedStatement = null;
				  
				  for(int i=0;i<pageDomain.getElementDomainList().size();i++){
				   
					 preparedStatement = conn.prepareStatement(INSERT_ELEMENT);
				   
				     preparedStatement.setString(1, pageDomain.getElementDomainList().get(i).getContent());
			  		 preparedStatement.setString(2, pageDomain.getElementDomainList().get(i).getXcord());
					 preparedStatement.setString(3, pageDomain.getElementDomainList().get(i).getYcord());
					 preparedStatement.setString(4, pageDomain.getElementDomainList().get(i).getElementUniqueId());
					 preparedStatement.setString(5, uuid);
					 preparedStatement.setString(6, pageDomain.getElementDomainList().get(i).getType());
					 preparedStatement.executeUpdate();
				  }
				  
				   if(preparedStatement!=null) {
		        	   preparedStatement.close();
		           }
				   
				  PreparedStatement preparedStatementtwo = conn.prepareStatement(UPDATE_PAGE);
				  preparedStatementtwo.setString(1, pageDomain.getName());
				  preparedStatementtwo.setString(2, pageDomain.getUuid());
				  preparedStatementtwo.executeUpdate();
				  preparedStatementtwo.close();
				  preparedStatementPage.close();
				
			} catch (SQLException e) {
				log.info(e);
				throw new RuntimeException(e);		
			}
		return pageDomain;
	}

	public ArrayList<PageDomain> getpages() {
		
			Connection conn;
			ArrayList<PageDomain> list = new ArrayList<PageDomain>();
			try {
				conn = pool.getConnectionFromPool();
				PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_PAGES);
				
				ResultSet resultSet = preparedStatement.executeQuery();
				if (!resultSet.isBeforeFirst()) {
					resultSet.close();
					preparedStatement.close();
				} else {
					while(resultSet.next()){
						PageDomain pageDomain = new PageDomain();
						pageDomain.setName(resultSet.getString("name"));
						pageDomain.setUuid(resultSet.getString("UniqueId"));
						pageDomain.setElementDomainList(getElementList(conn, pageDomain.getUuid()));
						list.add(pageDomain);
					}
				}
			} catch (SQLException e) {
				log.info(e);
				throw new DbException("DataBase exception thrown",e);
			}	
		 return list;
	}
					
	
	public ArrayList<ElementDomain> getElementList(Connection conn,String uuid) {
		
		ArrayList<ElementDomain> OldElementDomainlist = new ArrayList<ElementDomain>();
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(GET_ELEMENT_LIST);
			preparedStatement.setString(1, uuid);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.isBeforeFirst()) {
					resultSet.close();
					preparedStatement.close();
				} else {
					  while (resultSet.next()) {
					   ElementDomain elementDomain = new ElementDomain();
					   elementDomain.setElementUniqueId(resultSet.getString("eUniqueId"));
					   elementDomain.setPageUniqueId(resultSet.getString("pUniqueId"));
					   elementDomain.setType(resultSet.getString("type"));
					   elementDomain.setXcord(resultSet.getString("xcord"));
					   elementDomain.setYcord(resultSet.getString("ycord"));
	                   elementDomain.setContent(resultSet.getString("content"));
	                   
	                   
	                   OldElementDomainlist.add(elementDomain);
                    }
					resultSet.close();
					preparedStatement.close();
			}
		} catch (SQLException e) {
			log.info(e);
		}
		return OldElementDomainlist;	
	}

	public PageDomain getPage(String uniqueId) {
		Connection conn;
		PageDomain pageDomain=null;
		try {
			conn = pool.getConnectionFromPool();
			PreparedStatement preparedStatement = conn.prepareStatement(SELECT_PAGE);
			preparedStatement.setString(1, uniqueId);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.isBeforeFirst()) {
				resultSet.close();
				preparedStatement.close();
			} 
			else 
			{
				while (resultSet.next())
				{		
					pageDomain = new PageDomain();
					pageDomain.setName(resultSet.getString("name"));
					pageDomain.setUuid(resultSet.getString("uniqueId"));
					pageDomain.setElementDomainList(getElementList(conn, pageDomain.getUuid()));
				}
				resultSet.close();
				preparedStatement.close();
		  }
		} catch (SQLException e) {
			log.info(e);
			throw new DbException("DataBase exception thrown",e);
		}	
		return pageDomain;
	}
}