package ptud.Entity;

import java.util.ArrayList;
import ptud.DAO.DAO_CongDoan;

public class SanPham 
{
	// Author VoPhuocHau
  String maHD;
  DAO_CongDoan daocd = new DAO_CongDoan();
  ArrayList<CongDoan> congDoans = new ArrayList<>();

    public String getMaHD() 
    {
        return maHD;
    }

    public void setMaHD(String maHD) 
    {
        this.maHD = maHD;
    }
  String maSanPham;
  String tenSanPham;
  int soLuong;
  double donGia;
  int tienDo;
    public ArrayList<CongDoan> getCongDoans() {
        return congDoans;
    }

    public void updateListCongDoans() 
    {
       for(CongDoan congDoan : daocd.getAll())
       {
           if(congDoan.getMaSP().compareToIgnoreCase(this.maSanPham)==0)
           {
               if(!congDoans.contains(congDoan))
               {
                   congDoans.add(congDoan);
               }
           }
       }
    }

    public int getTienDo() {
        return tienDo;
    }

    public void setTienDo() 
    {        
        updateListCongDoans();
        int tienDo;
        if (congDoans.size() != 0) {
            String max = congDoans.get(0).getMaCD();
            tienDo = congDoans.get(0).getSoLuongHoanThanh();
            System.out.println(congDoans.get(0).getSoLuongHoanThanh());
            for (CongDoan congDoan : congDoans) {
                if (congDoan.getMaCD().compareToIgnoreCase(max) > 0) {
                    max = congDoan.getMaCD();
                    tienDo = congDoan.getSoLuongHoanThanh();
                    System.out.println(congDoan.getSoLuongHoanThanh());
                }

                this.tienDo = tienDo;              
            }
        } 
        else 
        {
             this.tienDo = 0;
        }
    }

  public String getMaSanPham() 
    {
		return maSanPham;
	}
	public void setMaSanPham(int stt) 
	{
		if(stt<10)
		{
                    String sttString = "0"+stt;
			this.maSanPham = this.maHD+sttString;
		}
		else 
		{  
                    String sttString = ""+stt;
                    this.maSanPham = this.maHD+stt;
		}
	}
	public String getTenSanPham() {
		return tenSanPham;
	}
	public void setTenSanPham(String tenSanPham) {
		if(tenSanPham.trim().equals(""))
		{
			tenSanPham = "Khong xac dinh";
		}
		this.tenSanPham = tenSanPham;
	}
	public int getSoLuong() 
	{
		
		return soLuong;
	}
	public void setSoLuong(int soLuong) 
	{
		if(soLuong<0)
		{
			soLuong = 0;
		}
		this.soLuong = soLuong;
	}
	public double getDonGia() 
	{
		
		return donGia;
	}
	public void setDonGia(double donGia) 
	{
		if(donGia<0)
		{
			donGia = 0;
		}
		this.donGia = donGia;
	}
        public void setMaSanPham(String maSanPham)
        {
            this.maSanPham = maSanPham;
        }
	public SanPham(String maSanPham, String tenSanPham, int soLuong, double donGia,String maHD) 
	{
		super();
		this.setDonGia(donGia);               
		this.setMaSanPham(maSanPham);
		this.setSoLuong(soLuong);
		this.setTenSanPham(tenSanPham);
                this.setMaHD(maHD);
	}
        public SanPham(int stt, String tenSanPham, int soLuong, double donGia,String maHD) 
	{
		super();
                this.setMaHD(maHD);
		this.setDonGia(donGia);               		
		this.setSoLuong(soLuong);
		this.setTenSanPham(tenSanPham);
                this.setMaSanPham(stt);
               
	}
}
