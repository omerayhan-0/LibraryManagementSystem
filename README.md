# 📚 Kütüphane Yönetim Sistemi (YMT)

Bu proje, **Java**, **JavaFX** ve **MySQL** kullanılarak geliştirilmiş masaüstü tabanlı bir **Kütüphane Yönetim Sistemi**dir.
Proje, *Yazılım Tasarımı ve Mimarisi (Software Design & Architecture)* dersi kapsamında geliştirilmiştir.

---

## 🚀 Proje Özellikleri

### 👤 Üye (Member)

* Giriş (Login) sistemi
* Kitap arama
* Kitap ödünç alma
* Ödünç alınan kitapları görüntüleme
* İade durumunu takip etme
* Profil Bilgilerini Güncelleme

### 👨‍💼 Personel (Staff / Admin)

* Kitap ekleme / güncelleme / silme 
* Üye yönetimi (Üye kaydı / Üye listesini görüntüleme)
* Ödünç alma ve iade işlemlerini yönetme
* Tüm ödünç kayıtlarını görüntüleme
* Üyeye ait işlemleri gerçekleştirme
* Personel Ekleme

---

## 🧱 Mimari ve Tasarım

* **JavaFX** – Kullanıcı Arayüzü
* **MySQL** – Veritabanı
* **JDBC** – Veritabanı bağlantısı
* **MVC (Model-View-Controller)** mimarisi

### Kullanılan Tasarım Desenleri

* **Singleton** – Veritabanı bağlantısının tekil yönetimi
* **Observer** – Bildirim ve güncelleme mekanizması
* **State** – Kullanıcı rollerinin (Üye / Personel) yönetimi
* **DAO** – Veritabanı erişimini soyutlayan katmandır.
* **Factory Pattern** – Kullanıcı rolüne göre (Member / Staff) uygun nesnenin oluşturulmasını sağlar.

---

## 🗄 Veritabanı Tasarımı

Projede aşağıdaki temel tablolar kullanılmaktadır:

* `books`
* `members`
* `staff`
* `borrow`

Veritabanı, **ER diyagramı** kullanılarak modellenmiş ve **foreign key** ilişkileri ile veri bütünlüğü sağlanmıştır.

---


## 🧪 Kullanıcı Rolleri

| Rol      | Açıklama                              |
| -------- | ------------------------------------- |
| Üye      | Kitap arayabilir ve ödünç alabilir    |
| Personel | Kitap ve kullanıcı yönetimi yapabilir |

---

## 🛠 Kullanılan Teknolojiler

* Java 21+
* JavaFX
* MySQL
* JDBC
* Git & GitHub

---


## 👨‍🎓 Geliştirici

**Ahmet Melih Ekinci** -> https://github.com/meliheknciii
**Ferhat Melik Aydın** -> https://github.com/nanchash
**Ömer Faruk Ayhan** -> https://github.com/omerayhan-0


