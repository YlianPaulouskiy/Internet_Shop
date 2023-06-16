package by.itacademy.shop;


import by.itacademy.shop.dao.OrderDao;

public class App {

    public static void main(String[] args) {

        OrderDao.getInstance().findAllByUserId(1L).forEach(System.out::println);

    }
}
