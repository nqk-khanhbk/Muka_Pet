Đóng gói trên docker

1.docker build -t muka-pet-demo:0.0.1 . 

2. Sửa lỗi kết nối tới DB
   docker run --name pet-muka-run-demo -p 8087:8087 -e DB_CONNECTION=jdbc:mysql://172.17.0.3:3306/muka pet-muka-testdemo:0.0.1 

3. tạo network
   docker network create muka-project-demo
4. docker run --network muka-project-demo --name mysql-demo-muka -p 3308:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.36-debian
