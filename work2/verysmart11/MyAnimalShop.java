package work2;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class MyAnimalShop implements AnimalShop {

    protected double balance;
    protected double startBalance;
    protected ArrayList<Animal> animalList;
    protected ArrayList<Customer> customerList;
    protected boolean isClose;

    protected double profit;

    protected MyAnimalShop(double balance, ArrayList<Animal> animalList, ArrayList<Customer> customerList) {
        this.balance = balance;
        this.animalList = animalList;
        this.customerList = customerList;
        startBalance = balance;
    }

    protected double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        balance = balance;
    }

    protected ArrayList<Animal> getAnimalList() {
        return animalList;
    }

    protected void setAnimalList(ArrayList<Animal> animalList) {
        animalList = animalList;
    }

    protected ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    protected void setCustomerList(ArrayList<Customer> customerList) {
        customerList = customerList;
    }

    protected boolean getIsClose() {
        return isClose;
    }

    protected void setIsClose(boolean close) {
        isClose = close;
    }
    @Override
    public void buyAnimal(Animal a) throws InsufficientBalanceException {
        if (this.isClose == false) {
            if (balance >= a.Price) {
                balance -= a.Price;
                animalList.add(a);
                System.out.print("buy a ");
                if (a instanceof Cat) System.out.println("cat");
                else if (a instanceof ChinesePastoralDog) System.out.println("dog");
                else if (a instanceof Rabbit) System.out.println("rabbit");
            } else {
                InsufficientBalanceException e = new InsufficientBalanceException();
                throw new InsufficientBalanceException("Insufficient balance.");
            }
        } else System.out.println("Store is closing");
    }

    @Override
    public void treatCustomer(Customer c,Scanner sc) {
        if (this.isClose == false) {
            boolean flag = false;
            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i).name.equals(c.name)) {
                    flag = true;
                }
            }
            if (flag == false) {
                customerList.add(c);
            }
            c.numberOfVisits++;
            c.latestArrivalTime = LocalDate.now();
            System.out.println("\r\n\r\n\r\n\r\n");
            System.out.println(c.toString());
            if (animalList.size() == 0) {
                throw new AnimalNotFountException("There are no animals in the store.");
            } else {
                System.out.println("All animals in our store :");
                for (int i = 0; i < animalList.size(); i++) {
                    System.out.println((i + 1) + ". " + animalList.get(i).toString());
                }
                System.out.println("Please input the number of the animal you want to choose.");
                int Choice = sc.nextInt();
                for (int i = 0; i < animalList.size(); i++) {
                    if (Choice == (i + 1)) {
                        System.out.println(c.name + " buys" + animalList.get(i).name);
                        System.out.println("enter an item in an account" + animalList.get(i).Price);
                        profit += animalList.get(i).Price;
                        animalList.remove(i);
                    }
                }
            }
        } else System.out.println("Store is closing");




    }

    @Override
    public void close(LocalTime time) throws ParseException {
        if (time.isBefore(LocalTime.of(10, 0))) {
            System.out.println("It is closing time");
            this.isClose = true;

        } else if (time.isAfter(LocalTime.of(22, 0))) {
            System.out.println("It is closing time");
            System.out.println("the profit in today is " + profit);
            boolean flag = false;
            for (int i = 0; i < customerList.size(); i++) {
                if (customerList.get(i).latestArrivalTime.equals(LocalDate.now())) {
                    customerList.get(i).toString();
                    flag = true;
                }
            }
            if (flag == false) System.out.println("there is no customer today");
            this.isClose=true;
        } else if(time.isBefore(LocalTime.of(22, 0))&&time.isAfter(LocalTime.of(10, 0))){
            System.out.println("It is opening time");
            this.isClose = false;
        }
    }
}
