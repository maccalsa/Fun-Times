## Inital thoughts

This core of this task is how to apply discounts to a basket. The problem can be thought of as an **input** -> **process** -> **output** issue. So my aim is follow something like

                         Discounts
                             |
                             |
                             V

**Shopping Basket** -----> **Basket Calculator** -------> **Checkout Basket**

where:

- _Shopping Basket_ : responsible for recording the products and the quantity of each prodcut.
- _Checkout Basket_ : responsible for recording a line item for each product plus any discount applied (Will probaly need to know what "today" is)
- _Basket Calculator_ : Takes a Shopping Basket and converts it to a Checkout Basket, applying discounts where appropriate

## Some Assumptions

1. Given the scope of this task, I will not be using a database.
2. I assume that only 1 discount at a time can ever be applied to a single basket entry. This will be in a first come, first served basis.
   1. For the acceptance criteria to pass this will not be an issue.
3. Discounts are valid on a closed range of the from and to date
   1. For the acceptance criteria to pass this will not be an issue.

## Up and running

the application has a complement of unit tests and a cucumber test that verifiy all scenarios. It comes with mvn, so no mvn installation is required. The jar executable however it is not a fat jar, so will need a local mvn repsitory.

### Unit + cucumber tests can be run using the following.

`mvn clean test`

#### The application can be built using the following

`mvn clean package`

## The application can be started using

`java -jar target/groceries-1.0-SNAPSHOT.jar`
