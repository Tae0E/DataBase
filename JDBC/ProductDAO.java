import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql;
	private void connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (ClassNotFoundException e) {
			System.err.println("ClassNotFoundException : " + e.getMessage());
		}
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@dbserver.yu.ac.kr:1521:XE", "student182", "xodudl159753");
		} 
		catch (SQLException sqle) {
			System.out.println("SQLException : " + sqle);
		}
	}
	private void disconnect() {
		try {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void insert(Product p) throws Exception
	{
		try {
			connect();
			sql = "insert into product(pid, pname, manufacturer, category, regdate, price, salenum) values (?,?,?,?,to_date(?, 'YYYY-MM-DD'),?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p.getPID());
			pstmt.setString(2, p.getPNAME());
			pstmt.setString(3, p.getMANUFACTURER());
			pstmt.setString(4, p.getCATEGORY());
			pstmt.setString(5, p.getREGDATE());
			pstmt.setInt(6, p.getPRICE());
			pstmt.setInt(7, p.getSALENUM());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new Exception("Product 추가 오류", e);
		} finally { disconnect(); }
	}
	public void delete(int pid) throws Exception
	{
		ResultSet rs = null;
		try {
			connect();
			sql = "select * from product where pid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				if(rs.getInt("pid") == pid)
				{
					pstmt = conn.prepareStatement("delete from product where pid = ?");
					pstmt.setInt(1, pid);
					pstmt.executeUpdate();
					break;
				}
			}
		} catch (Exception e) {
			throw new Exception("delete 오류", e);
		} finally { disconnect(); }
	}
	@SuppressWarnings("resource")
	public boolean update(int pid, int pprice, int psalenum) throws Exception
	{
		int price = 0;
		int salenum = 0;
		ResultSet rs = null;
		try {
			connect();
			sql = "select price, salenum from product where pid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				price = rs.getInt("price");
				salenum = rs.getInt("salenum");
				if (pprice < 0 || psalenum < 0)
					return false;
				else 
				{
					price = pprice;
					salenum = psalenum;
				}
				
				sql = "update product set price = ?, salenum = ? where pid = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, price);
				pstmt.setInt(2, salenum);
				pstmt.setInt(3, pid);
				pstmt.executeUpdate();
				
				sql = "select price, salenum from product where pid = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, pid);
				rs = pstmt.executeQuery();
			}
		} catch (Exception e) {
			throw new Exception("가격, 판매 건수 갱신 오류", e);
		} finally { if (rs != null) rs.close(); disconnect(); }
		return true;
	}
	public Product get(int pid) throws Exception
	{
		Product p = new Product();
		ResultSet rs = null;
		try 
		{
			connect();
			sql = "select * from product where pid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pid);
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				if(rs.getInt("pid") == pid)
				{
					p.setPID(rs.getInt("pid"));
					p.setPNAME(rs.getString("pname"));
					p.setMANUFACTURER(rs.getString("manufacturer"));
					p.setCATEGORY(rs.getString("category"));
					p.setREGDATE(rs.getString("regdate"));
					p.setPRICE(rs.getInt("price"));
					p.setSALENUM(rs.getInt("salenum"));
					break;
				}
			}	
		} 
		catch (Exception e) 
		{
			throw new Exception("Product 갱신 오류", e);
		} finally { if (rs != null) rs.close(); disconnect(); }
		return p;
	}
	public List<Product> getList() throws Exception
	{
		ArrayList<Product> list = null;
		ResultSet rs = null;
		
		try {
			connect();
			sql = "select * from product order by pid";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<Product>();
				Product p = new Product();
				p.setPID(rs.getInt("pid"));
				p.setPNAME(rs.getString("pname"));
				p.setMANUFACTURER(rs.getString("manufacturer"));
				p.setCATEGORY(rs.getString("category"));
				p.setREGDATE(rs.getString("regdate"));
				p.setPRICE(rs.getInt("price"));
				p.setSALENUM(rs.getInt("salenum"));
				list.add(p);
			}
		} catch (Exception e) {
			throw new Exception("전체 계좌 리스트 검색 오류", e);
		} finally { if (rs != null) rs.close(); disconnect(); }
		
		return list;
	}
}
