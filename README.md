### Implementation of Postgres LTree in Spring Boot app
#### First create the database and a super user manually 
```
CREATE DATABASE tmdb;
CREATE USER tmc WITH ENCRYPTED PASSWORD 'tmc';
GRANT ALL PRIVILEGES ON DATABASE tmdb TO tmc;
ALTER USER tmc WITH SUPERUSER;
```


##### Sample request and response
```
POST http://localhost:8080/content-boost
[
	{
      "path": "order_1",
      "boost": 20,
      "rules": [
        {
          "placement": "ad_placement_brochureBar",
          "visible": true,
          "boost": 100
        },
        {
          "placement": "ad_placement_lastPage",
          "visible": false,
          "boost": -30
        }
      ]
    },
    {
      "path": "order_1.order_item_1",
      "boost": 30,
      "rules": [
        {
          "placement": "ad_placement_brochureBar",
          "visible": true,
          "boost": -10
        }
      ]
    },
    {
      "path": "order_1.order_item_1.content_1",
      "boost": 60,
      "rules": [
        {
          "placement": "ad_placement_brochureBar",
          "visible": true,
          "boost": -60
        }
      ]
    },
    {
      "path": "order_1.order_item_1.content_2",
      "boost": 60,
      "rules": [
        {
          "placement": "ad_placement_brochureBar",
          "visible": true,
          "boost": -60
        }
      ]
    }
]


GET http://localhost:8080/content-boost/order_1
[
    {
        "path": "order_1",
        "boost": 20,
        "rules": [
            {
                "placement": "ad_placement_brochureBar",
                "visible": true,
                "boost": 100
            },
            {
                "placement": "ad_placement_lastPage",
                "visible": false,
                "boost": -30
            }
        ]
    },
    {
        "path": "order_1.order_item_1",
        "boost": 30,
        "rules": [
            {
                "placement": "ad_placement_brochureBar",
                "visible": true,
                "boost": -10
            }
        ]
    },
    {
        "path": "order_1.order_item_1.content_1",
        "boost": 60,
        "rules": [
            {
                "placement": "ad_placement_brochureBar",
                "visible": true,
                "boost": -60
            }
        ]
    },
    {
        "path": "order_1.order_item_1.content_2",
        "boost": 60,
        "rules": [
            {
                "placement": "ad_placement_brochureBar",
                "visible": true,
                "boost": -60
            }
        ]
    }
]
```
