use  ConnectSocialNetworkProject

select * from users

insert into users values ("admin","cukelli","cukelli@gmail.com","Anastasija",'2023-04-28 12:30:45',"Cukelj,",
"12345",null,"ADMIN")

insert into users values ("user","glavonja","glavonja@gmail.com","Jovan",'2023-04-28 12:30:45',"Glavonjic,",
"12345",null,"USER")

UPDATE users
SET last_name = 'Maric'
WHERE username = 'miciiiiii';