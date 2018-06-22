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
				System.out.print("select mode(0:����, 1:����, 2:����, 3:�˻�, 4:����) : ");
				selectnum=inputi.nextInt();
				if(selectnum==0)
				{
					System.out.println("**ī�װ� �Է� �� food, clothes, homeapp, sundries, book �߿��� �Է��� �ּ���**");
					System.out.println("**��¥ ������ 1994-07-06 �������� �Է��� �ּ���**");
					System.out.print("id : ");
					pid = inputi.nextInt();
					System.out.print("��ǰ�� : ");
					pname = inputs.next();
					System.out.print("������ : ");
					manufacturer = inputs.next();
					System.out.print("ī�װ� : ");
					category = inputs.next();
					System.out.print("����� : ");
					regdate = inputs.next();
					System.out.print("���� : ");
					price = inputi.nextInt();
					System.out.print("�Ǹ� �Ǽ� : ");
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
					System.out.print("������ id : ");
					pid = inputi.nextInt();				
					pdao.delete(pid);
				}
				else if(selectnum==2)
				{
					System.out.print("id : ");
					pid = inputi.nextInt();
					System.out.print("���� : ");
					price = inputi.nextInt();
					System.out.print("�Ǹ� �Ǽ� : ");
					salenum = inputi.nextInt();
					pdao.update(pid, price, salenum);
				}
				else if(selectnum==3)
				{
					System.out.print("�˻��� id : ");
					pid = inputi.nextInt();
					p = pdao.get(pid);
					
					System.out.println("id : " + p.pid);
					System.out.println("��ǰ�� : " + p.pname);
					System.out.println("������ : " + p.manufacturer);
					System.out.println("ī�װ� : " + p.category);
					System.out.println("����� : " + p.regdate);
					System.out.println("���� : " + p.price);
					System.out.println("�Ǹ� �Ǽ� : " + p.salenum);
				}
				else if(selectnum==4)
				{
					System.out.println("���α׷��� �����մϴ�.");
					break;
				}
				else
				{
					System.out.println("�߸��� ��带 �Է��Ͽ����ϴ�!");
				}
			}	
		} 
		catch (SQLException sqle) {
			System.out.println("SQLException : " + sqle);
		}
	}

}
