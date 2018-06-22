import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ProductJDBCTest{

	public static void main(String[] args) throws Exception {
		try {
			int selectnum;
			int pid;
			String pname;
			String manufacturer;
			String category;
			String regdate;
			int price;
			int salenum;
			Product p = new Product();
			ProductDAO pdao = new ProductDAO();
			Scanner inputi = new Scanner(System.in);
			Scanner inputs = new Scanner(System.in);
			while(true)
			{
				System.out.print("select mode(0:삽입, 1:삭제, 2:갱신, 3:검색, 4:종료) : ");
				selectnum=inputi.nextInt();
				if(selectnum==0)
				{
					System.out.println("**카테고리 입력 시 food, clothes, homeapp, sundries, book 중에서 입력해 주세요**");
					System.out.println("**날짜 형식은 1994-07-06 형식으로 입력해 주세요**");
					System.out.print("id : ");
					pid = inputi.nextInt();
					System.out.print("상품명 : ");
					pname = inputs.next();
					System.out.print("제조사 : ");
					manufacturer = inputs.next();
					System.out.print("카테고리 : ");
					category = inputs.next();
					System.out.print("등록일 : ");
					regdate = inputs.next();
					System.out.print("가격 : ");
					price = inputi.nextInt();
					System.out.print("판매 건수 : ");
					salenum = inputi.nextInt();

					p.setPID(pid);
					p.setPNAME(pname);
					p.setMANUFACTURER(manufacturer);
					p.setCATEGORY(category);
					p.setREGDATE(regdate);
					p.setPRICE(price);
					p.setSALENUM(salenum);
					
					pdao.insert(p);
					
				}
				else if(selectnum==1)
				{
					System.out.print("삭제할 id : ");
					pid = inputi.nextInt();				
					pdao.delete(pid);
				}
				else if(selectnum==2)
				{
					System.out.print("id : ");
					pid = inputi.nextInt();
					System.out.print("가격 : ");
					price = inputi.nextInt();
					System.out.print("판매 건수 : ");
					salenum = inputi.nextInt();
					pdao.update(pid, price, salenum);
				}
				else if(selectnum==3)
				{
					System.out.print("검색할 id : ");
					pid = inputi.nextInt();
					p = pdao.get(pid);
					
					System.out.println("id : " + p.pid);
					System.out.println("상품명 : " + p.pname);
					System.out.println("제조사 : " + p.manufacturer);
					System.out.println("카테고리 : " + p.category);
					System.out.println("등록일 : " + p.regdate);
					System.out.println("가격 : " + p.price);
					System.out.println("판매 건수 : " + p.salenum);
				}
				else if(selectnum==4)
				{
					System.out.println("프로그램을 종료합니다.");
					break;
				}
				else
				{
					System.out.println("잘못된 모드를 입력하였습니다!");
				}
			}	
		} 
		catch (SQLException sqle) {
			System.out.println("SQLException : " + sqle);
		}
	}

}
