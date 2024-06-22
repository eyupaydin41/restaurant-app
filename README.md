# Restaurant Microservices Projesi

Bu proje, restoran sistemine yönelik mikroservis tabanlı bir uygulamadır. Proje, kullanıcı hizmetleri, restoran hizmetleri ve bir API geçidi (gateway) gibi çeşitli mikroservislerden oluşmaktadır. Her mikroservis bağımsız olarak çalışabilir ve belirli işlevleri yerine getirir.

## Genel Bakış

Restaurant Microservices Projesi, kullanıcılara restoranlarla ilgili çeşitli hizmetler sunmayı amaçlayan bir uygulamadır. Bu proje, kullanıcıların kaydolması, restoranları ve yorumları yönetmesi gibi işlevleri içerir. API Gateway, tüm istekleri yönlendirir ve mikroservislerin birbirleriyle iletişim kurmasını sağlar.

## Mikroservisler

### Kullanıcı Servisi

Kullanıcı servisi, kullanıcıların kaydolması, güncellenmesi, silinmesi ve kullanıcı bilgilerini alması gibi işlemleri yönetir.

#### Kullanılan Teknolojiler
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- ModelMapper
- SpringDoc OpenAPI
- Lombok

#### API Endpoints
- `GET /user`: Tüm kullanıcıları getirir.
- `GET /user/{id}`: Belirtilen ID'ye sahip kullanıcıyı getirir.
- `POST /user`: Yeni kullanıcı oluşturur.
- `PUT /user`: Var olan kullanıcıyı günceller.
- `DELETE /user`: Belirtilen ID'ye sahip kullanıcıyı siler.


### Restoran Servisi

Restoran servisi, restoranların kaydolması, güncellenmesi, silinmesi ve restoran bilgilerini alması gibi işlemleri yönetir. Aynı zamanda restoran yorumlarını da yönetir.

#### Kullanılan Teknolojiler

-   Spring Boot
-   Spring Data JPA
-   Hibernate
-   MySQL
-   ModelMapper
-   SpringDoc OpenAPI
-   Lombok

#### API Endpoints

##### Restoranlar

-   `GET /restaurant`: Tüm restoranları getirir.
-   `GET /restaurant/{id}`: Belirtilen ID'ye sahip restoranı getirir.
-   `POST /restaurant`: Yeni restoran oluşturur.
-   `PUT /restaurant`: Var olan restoranı günceller.
-   `DELETE /restaurant`: Belirtilen ID'ye sahip restoranı siler.

##### Yorumlar

-   `GET /reviews`: Tüm yorumları getirir.
-   `GET /reviews/{id}`: Belirtilen ID'ye sahip yorumu getirir.
-   `POST /reviews`: Yeni yorum oluşturur.
-   `PUT /reviews`: Var olan yorumu günceller.
-   `DELETE /reviews`: Belirtilen ID'ye sahip yorumu siler.
### API Gateway

API Gateway, gelen istekleri ilgili mikroservislere yönlendiren bir geçit servisidir.

#### Kullanılan Teknolojiler
- Spring Cloud Gateway
- SpringDoc OpenAPI
- CORS Konfigürasyonu

## Kurulum

Projeyi klonlayın:
```bash
git clone https://github.com/eyupaydin41/restaurant-app.git
cd restaurant-app
```
Her mikroservisin bağımlılıklarını yüklemek ve uygulamayı çalıştırmak için Maven kullanın:
```bash
mvn clean install
mvn spring-boot:run
```

## Kullanım

1. **Kullanıcı Servisi**: Kullanıcı kayıt, güncelleme, silme ve listeleme işlemlerini yapar.
2. **Restoran Servisi**: Restoran ve yorum yönetimi sağlar.
3. **API Gateway**: Tüm istekleri ilgili mikroservislere yönlendirir.

## Yapılandırma

Uygulamanın yapılandırma dosyaları (`application.properties` veya `application.yml`) her mikroservisin ana kaynak dizininde bulunur. Veritabanı bağlantı ayarlarını ve diğer yapılandırma bilgilerini burada bulabilirsiniz.

## Bağımlılıklar

- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- Spring Boot Starter Web
- Spring Boot Starter Test
- Spring Boot Starter Webflux
- Spring Cloud Starter Gateway
- Lombok
- MySQL Connector
- Hibernate
- ModelMapper
- Reactor Core
- SpringDoc OpenAPI
- Jakarta Servlet API

## Swagger Dökümantasyonu

Her mikroservis, Swagger UI aracılığıyla API dökümantasyonuna sahiptir.

- **Kullanıcı Servisi**: `http://localhost:8081/user/swagger-ui.html`
- **Restoran Servisi**: `http://localhost:8082/restaurant/swagger-ui.html`
- **API Gateway**: `http://localhost:8045/webjars/swagger-ui/index.html`

