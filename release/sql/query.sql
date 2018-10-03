
(1) Write MySQL query to find IPs that mode more than a certain number of requests for a given time period.
##########################################
    select
        IP,
        count(IP) as count 
    from
        logrecords  
    where
        startDate between '2017-01-01 13:00:00' and '2017-01-01 14:00:00' 
    group by
        IP 
    having
        count(IP)>100
#########################################        

(2) Write MySQL query to find requests made by a given IP.
##########################################
    select
        request
    from
        logrecords  
    where
        IP = '192.168.77.101'
#########################################        

