package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.AdvancedSearchDAL;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AdvancedSearchBUS {

    private final AdvancedSearchDAL dal =
            new AdvancedSearchDAL();

    public List<String[]> searchProducts(
            String ten,
            BigDecimal giaTu,
            BigDecimal giaDen,
            Integer soLuongTu,
            Integer soLuongDen,
            int page,
            int pageSize,
            String sortColumn,
            boolean ascending) throws SQLException {

        return dal.searchProducts(
                ten,
                giaTu,
                giaDen,
                soLuongTu,
                soLuongDen,
                page,
                pageSize,
                sortColumn,
                ascending
        );
    }

    public int countProducts(
            String ten,
            BigDecimal giaTu,
            BigDecimal giaDen,
            Integer soLuongTu,
            Integer soLuongDen) throws SQLException {

        return dal.countProducts(
                ten,
                giaTu,
                giaDen,
                soLuongTu,
                soLuongDen
        );
    }

    public List<String[]> searchCustomers(
            String ten,
            String sdt,
            String diaChi,
            int page,
            int pageSize) throws SQLException {

        return dal.searchCustomers(
                ten,
                sdt,
                diaChi,
                page,
                pageSize
        );
    }

    public int countCustomers(
            String ten,
            String sdt,
            String diaChi) throws SQLException {

        return dal.countCustomers(
                ten,
                sdt,
                diaChi
        );
    }

    public List<String[]> searchInvoices(
            Date tuNgay,
            Date denNgay,
            Integer maKh,
            BigDecimal tongTu,
            BigDecimal tongDen,
            int page,
            int pageSize) throws SQLException {

        return dal.searchInvoices(
                tuNgay,
                denNgay,
                maKh,
                tongTu,
                tongDen,
                page,
                pageSize
        );
    }

    public int countInvoices(
            Date tuNgay,
            Date denNgay,
            Integer maKh,
            BigDecimal tongTu,
            BigDecimal tongDen) throws SQLException {

        return dal.countInvoices(
                tuNgay,
                denNgay,
                maKh,
                tongTu,
                tongDen
        );
    }
}