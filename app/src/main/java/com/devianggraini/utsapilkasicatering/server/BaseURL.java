package com.devianggraini.utsapilkasicatering.server;

public class BaseURL {
    public static String baseUrl = "http://192.168.43.66:2019/";

    public static String login = baseUrl + "user/login";
    public static String register = baseUrl + "user/registrasi";

    //makanan
    public static String dataMenu = baseUrl +"makanan/dataMenu";

    public static String dataKategoriMenu = baseUrl +"makanan/datamakanan/appetizer";
    public static String dataKategoriMenuMain = baseUrl +"makanan/datamakanan/main course";
    public static String dataKategoriMenuDest = baseUrl +"makanan/datamakanan/dessert";
    public static String dataKategoriMenuDri = baseUrl +"makanan/datamakanan/drink";

    public static String editDataMenu = baseUrl +"makanan/ubah/";

    public static String hapusData = baseUrl +"makanan/hapus/";

    public static String inputMenu = baseUrl +"makanan/input";

    //order

    public static String order = baseUrl +"order/insert";
    public static String lihatDataTransaksi = baseUrl + "order/lihatTransaksi";
    public static String getdataTransaksi = baseUrl + "order/dataTransaksi/";
    public static String EditTransaksi = baseUrl + "order/ubah/";
}
