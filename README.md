# Planner micro   
1) Config (там хранятся необходимые параметры для запуска сервисов, сервис поставляет параметры для других сервисов) - [http://localhost:8888/](http://localhost:8888/)
2) Eureka-server (там можно посмотреть зарегистрированные сервисы) - [http://localhost:8761/](http://localhost:8761/)  
2) Eureka-client (Planner user) - сервис отвечает за работу с юзерами приложения - http://localhost:x/ (х - рандомный порт, который определяется на старте приложения)  
3) Eureka-client (Planner task) - сервис отвечает за работу с задачами - http://localhost:x/ (х - рандомный порт, который определяется на старте приложения)
4) Api Gateway (настроен роутинг) :  
   Eureka-client - [http://localhost:8765//planner-user](http://localhost:8765//planner-user) - для обращения к сервису Planner user   
   Eureka-client(2) - [http://localhost:8765//planner-task](http://localhost:8765//planner-task) - для обращения к сервису Planner task    
   



