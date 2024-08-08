package loginPageApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    Scanner input=new Scanner(System.in);
    List<String>emails=new ArrayList<>();
    List<String>passwords=new ArrayList<>();


    //3- ad soyad, email, sifre alıp listeye kaydedelim
    public void register(){
        System.out.println("Lutfen ad-soyad giriniz");
        String name=input.nextLine();
        //4- email gecersiz ise tekrar girilmeli
        String email;
        boolean isValid;
        do {
            System.out.println("Lutfen email adresinizi giriniz");
            email=input.nextLine();
            isValid=validateEmail(email);
            if (emails.contains(email)){
                System.out.println("bu email zaten kayıtlı lütfen baska bir email adresi giriniz");
                isValid=false;
            }
        }while (!isValid);
        String password;
        boolean isValidPass;
        do {
            System.out.println("Lutfen olusturmak istediginiz passwordu giriniz");
            password=input.nextLine();
            isValidPass=validatePassword(password);
        }while (!isValidPass);


        //6- user olusturma islemi
        User user =new User(password,name,email);
        emails.add(user.getEmail());
        passwords.add(user.getPassword());
        System.out.println("Tebrikler, kayıt işlemi başarıyla gerceklesti");
        System.out.println("Email ve sifreniz ile sisteme giris yapabilirsiniz");

    }


    public void login(){
        System.out.println("Lutfen giris yapmak icin email adresini giriniz");
        String email=input.nextLine();
        //girilen email listede var mi?
        boolean isExistEmail=emails.contains(email);
        if (isExistEmail){
            //kullanıcının kaydı vardir o zmn sifre kontrolu yapabiliriz
            int sayac=3;
            while(sayac>0){
                System.out.println("lutfen sifrenizi giriniz : ");//Alican123@
                String passw=input.nextLine();
                //sifre ile email eslesiyor mu
                int index=emails.indexOf(email);
                if (passwords.get(index).equals(passw)){//Alican123@==Alican123@;
                    System.out.println("Sisteme basarli bir sekilde giriş yaptınız. Hoşgeldiniz");
                    break;
                }else {
                    sayac--;
                    System.out.println("Sifreniz yanlış ya da eksik lutfne tekrar deneyiniz deneme hakkınız : "+sayac);
                    if (sayac==0){
                        //throw new passwordException
                    }
                }


            }
        }
    }

    private boolean validateEmail(String email){
        // email validation: boşluk içermemeli
        // : @ içermeli
        //                  : gmail.com,hotmail.com veya yahoo.com ile bitmeli.
        //                          : mailin kullanıcı adı kısmında(@ den önce) sadece büyük-küçük harf,rakam yada -._ sembolleri olabilir.
        boolean isValid = false;
        boolean space=email.contains(" ");
        boolean containsAt=email.contains("@");
        if (space){
            System.out.println("Email bosluk karakteri iceremez");
            isValid=false;
        }if(!containsAt){
            System.out.println("Email @ sembolunu icermelidir");
            isValid=false;
        }if (!space&&containsAt){//hem @ iceren hem de ' ' karakteri icermeyen bir durum soz konusu
            String firstPart=email.split("@")[0];
            String secondPart=email.split("@")[1];
            int notValidCharLength=firstPart.replaceAll("[a-zA-Z0-9-._]","").length();
            boolean checkFirst=notValidCharLength==0;
            boolean checkSecond=secondPart.equals("gmail.com")
                    ||secondPart.equals("hotmail.com")
                    ||secondPart.equals("yahoo.com");
            if (!checkFirst){
                System.out.println("Email harf,rakam ve -._ karakterleri disinda bir yada birden fazla karakter icermektedir");
            } else if (!checkSecond) {
                System.out.println("Email gmail.com, hotmail.com veya yahoo.com ile bitmelidir");
            }
            isValid=checkFirst&&checkSecond;
            if (!isValid){
                System.out.println("Gecersiz email, tekrar deneyiniz");
            }
        }
        return isValid;
    }

    private boolean validatePassword(String password){
        /*
         password validation: boşluk içermemeli
        : en az 6 karakter olmalı
        : en az bir tane küçük harf içermeli
        : en az bir tane büyük harf içermeli
        : en az bir tane rakam içermeli
        : en az bir tane sembol içermeli
         */
        boolean isValid;//alican123@
        boolean space=password.contains(" ");//false
        boolean length=password.length()>5;//true
        boolean kucukHarf=password.replaceAll("[^a-z]","").length()>0;//true
        boolean buyukHarf=password.replaceAll("[^A-Z]","").length()>0;//false
        boolean rakam=password.replaceAll("[^0-9]","").length()>0;//true
        boolean sembol=password.replaceAll("[\\P{Punct}]","").length()>0;//true
        if (space){
            System.out.println("Password bosluk karakteri iceremez");
        }
        if (!length){
            System.out.println("Password en az 6 karakter olmalıdır");
        }
        if (!kucukHarf){
            System.out.println("Password en az 1 tane kücük harf icermelidir");
        }
        if (!buyukHarf){
            System.out.println("Password en az 1 tane büyük harf icermelidir");
        }
        if (!rakam){
            System.out.println("Password en az 1 tane rakam icermelidir");
        }
        if (!sembol){
            System.out.println("Password en az 1 tane sembol icermelidir");
        }
        isValid=!space&&length&&kucukHarf&&buyukHarf&&rakam&&sembol;
        if (!isValid){
            System.out.println("gecersiz sifre,tekrar deneyiniz");
        }
        return isValid;
    }

}