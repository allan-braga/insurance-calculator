# insurance-calculator

To run the application you first need to build the code

> mvn clean install

Then you can run the application with the command

> mvn spring-boot:run

You can also run the application starting the main class 

> ProofItApplication.class

After run the application you can test it calling the POST endpoint

> http://localhost:8080/premium-calculator/v1/calculate

```
{
    "number" : "001",
    "status" : "APPROVED",
    "objects" : [
        {
            "name" : "HOME",
            "subObjects" : [
                {
                    "name" : "TV",
                    "insured" : 500.00,
                    "riskType" : "FIRE"
                },
                {
                    "name" : "TV",
                    "insured" : 102.51,
                    "riskType" : "THEFT"
                }
            ]
        }
    ]
}
```
