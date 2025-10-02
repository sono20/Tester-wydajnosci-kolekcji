import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public
class Main {
    public static void main(String[] args) {
        DataType dataType;
        DataGenerator<?> dataGenerator;
        Scanner scanner = new Scanner(System.in);
        System.out.println("*********************** \tMENU WYBORU\t *************************\n");

        System.out.println("Wybierz rodzaj danych wejściowych poprzez wpisanie jednej spośród następujących opcji:\nINTEGER\nDOUBLE\nPERSON\nMY_COLOR\nBOOK\nCAR\nANIMAL");
        String dataTypeScanner = scanner.next().toUpperCase();
        try {
            dataType = DataType.valueOf(dataTypeScanner);
            dataGenerator = switch (dataType) {
                case INTEGER -> new IntegerGenerator();
                case DOUBLE -> new DoubleGenerator();
                case PERSON -> new PersonGenerator();
                case MY_COLOR -> new MyColorGenerator();
                case BOOK -> new BookGenerator();
                case CAR -> new CarGenerator();
                case ANIMAL -> new AnimalGenerator();
            };
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Nieprawidłowy rodzaj danych wejściowych");
        }
        int count;
        System.out.println("Wybierz liczbę elementów poprzez wpisanie jednej spośród następujących opcji:\n100\n500\n1000\n10000\nJeśli podane opcje nie są odpowiednie, wpisz własną wartość:");
        try {
            int countScanner = scanner.nextInt();
            if (countScanner <= 0) {
                throw new IllegalArgumentException();
            }
            count = switch (countScanner) {
                case 100, 500, 1000, 10000 -> countScanner;
                default -> countScanner;
            };
        } catch (InputMismatchException e) {
            throw new RuntimeException("Podaj liczbę");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Podaj liczbę całkowitą dodatnią");
        }
        List<?> inputData = dataGenerator.generator(count);

        Collection<?> collection;
        CollectionType collectionType;
        System.out.println("Wybierz rodzaj kolekcji poprzez wpisanie jednej spośród następujących opcji:\nARRAY_LIST\nLINKED_LIST\nHASH_SET\nTREE_SET");
        String collectionTypeScanner = scanner.next().toUpperCase();
        try {
            collectionType = CollectionType.valueOf(collectionTypeScanner);
            collection = switch (collectionType) {
                case ARRAY_LIST -> new ArrayList<>();
                case LINKED_LIST -> new LinkedList<>();
                case HASH_SET -> new HashSet<>();
                case TREE_SET -> new TreeSet<>();
            };
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Nieprawidłowy rodzaj kolekcji.");
        }
        TestType testType;
        TestExecutor testExecutor;
        System.out.println("Wybierz rodzaj testu poprzez wpisanie jednej spośród następujących opcji:\nREAD_AFTER_INDEX\nINSERT_AND_REMOVE_FREQUENCY\nELEMENT_SEARCH\nEXISTENCE_OF_AN_ELEMENT");
        String testTypeScanner = scanner.next().toUpperCase();
        try {
            testType = TestType.valueOf(testTypeScanner);
            testExecutor = switch (testType) {
                case READ_AFTER_INDEX -> new ReadAfterIndexTest<>();
                case INSERT_AND_REMOVE_FREQUENCY -> new InsertAndRemoveTest<>();
                case ELEMENT_SEARCH -> new ElementSearchTest<>();
                case EXISTENCE_OF_AN_ELEMENT -> new ExistenceOfAnElementTest<>();
            };
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Nieprawidłowy rodzaj testu");
        }

        FormOfPresentation formOfPresentation;
        TestResultPresenter testResultPresenter;
        System.out.println("Wybierz formę prezentacji danych spośród następujących opcji:\nCONSOLE\nCSV");
        String formOfPresentationScanner = scanner.next().toUpperCase();
        try {
            formOfPresentation = FormOfPresentation.valueOf(formOfPresentationScanner);
            testResultPresenter = switch (formOfPresentation) {
                case CONSOLE -> new ConsoleTestResult();
                case CSV -> new CsvTestResult();
            };
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Nieprawidłowa forma prezentacji danych");
        }
        TestResult testResult = testExecutor.test(collection, inputData);
        testResultPresenter.result(testResult);
    }
}

enum DataType {
    INTEGER, DOUBLE, PERSON, MY_COLOR, BOOK, CAR, ANIMAL
}
enum CollectionType {
    ARRAY_LIST, LINKED_LIST, HASH_SET, TREE_SET
}
enum TestType{
    READ_AFTER_INDEX, INSERT_AND_REMOVE_FREQUENCY, ELEMENT_SEARCH, EXISTENCE_OF_AN_ELEMENT
}
enum FormOfPresentation {
    CONSOLE, CSV
}

class Animal implements Comparable<Animal> {
    private String name;
    private int age;
    private int birthYear;
    public Animal(String name, int age, int birthYear){
        this.name = name;
        this.age = age;
        this.birthYear = birthYear;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public int getBirthYear() {
        return birthYear;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age && birthYear == animal.birthYear && Objects.equals(name, animal.name);
    }
    @Override
    public int compareTo(Animal o) {
        int res = name.compareTo(o.name);
        return res != 0 ? res : birthYear - o.birthYear;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, age, birthYear);
    }
}

class Book implements Comparable<Book>{
    private String title;
    private double price;
    public Book(String title, double price){
        this.title = title;
        this.price = price;
    }
    public String getTitle() {
        return title;
    }
    public double getPrice() {
        return price;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Double.compare(price, book.price) == 0 && Objects.equals(title, book.title);
    }
    @Override
    public int hashCode() {
        return Objects.hash(title, price);
    }
    @Override
    public int compareTo(Book o) {
        int res = title.compareTo(o.title);
        return res != 0 ? res : Double.compare(price,o.price);
    }
}

class Car implements Comparable<Car>{
    private String brand;
    private int productionYear;
    private double price;
    public Car(String brand, int productionYear, double price){
        this.brand = brand;
        this.productionYear = productionYear;
        this.price = price;
    }
    public String getBrand(){
        return brand;
    }
    public int getProductionYear() {
        return productionYear;
    }
    public double getPrice() {
        return price;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return productionYear == car.productionYear && Double.compare(price, car.price) == 0 && Objects.equals(brand, car.brand);
    }
    @Override
    public int hashCode() {
        return Objects.hash(brand, productionYear, price);
    }
    @Override
    public int compareTo(Car o) {
        int res = brand.compareTo(o.brand);
        return res != 0 ? res : productionYear-o.productionYear;
    }
}

class MyColor implements Comparable<MyColor> {
    private int r;
    private int g;
    private int b;
    private int sum;
    public MyColor(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
        this.sum = r+g+b;
    }
    public int getR() {
        return r;
    }
    public int getG() {
        return g;
    }
    public int getB() {
        return b;
    }
    public int getSum() {
        return sum;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyColor myColor = (MyColor) o;
        return r == myColor.r && g == myColor.g && b == myColor.b && sum == myColor.sum;
    }
    @Override
    public int hashCode() {
        return Objects.hash(r, g, b, sum);
    }
    @Override
    public int compareTo(MyColor o) {
        int res = Integer.compare(r,o.r);
        return res != 0 ? res : sum-o.sum;
    }
}
class Person implements Comparable<Person> {
    private String name;
    private int birthYear;
    public Person(String name, int birthYear){
        this.name = name;
        this.birthYear = birthYear;
    }
    public String getName(){
        return name;
    }
    public int getBirthYear() {
        return birthYear;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return birthYear == person.birthYear && Objects.equals(name, person.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, birthYear);
    }
    @Override
    public int compareTo(Person o) {
        int res = name.compareTo(o.name);
        return res != 0 ? res : birthYear-o.birthYear;
    }
}


interface DataGenerator<T>{
    List<T> generator(int count);
}

class RandomString{
    public String generateString(){
        int length = 4+(int)(Math.random()*16);
        StringBuilder sb = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyz";
        sb.append((char)(Math.random()*26+'A'));
        for (int i=1;i<length;i++){
            sb.append(chars.charAt((int)(Math.random()*chars.length())));
        }
        return sb.toString();
    }
}
class AnimalGenerator implements DataGenerator<Animal>{
    private RandomString randomString = new RandomString();
    @Override
    public List<Animal> generator(int count){
        return Stream.generate(()-> {
                    String name = randomString.generateString();
                    int age = (int)(Math.random()*20)+1;
                    int birthYear = 2025-age;
                    return new Animal(name,age,birthYear);
                }).limit(count)
                .collect(Collectors.toList());
    }
}
class BookGenerator implements DataGenerator<Book>{
    private RandomString randomString = new RandomString();
    @Override
    public List<Book> generator(int count){
        return Stream.generate(()->{
                    String title = randomString.generateString();
                    double price = Math.random()*300+100;
                    return new Book(title,price);
                }).limit(count)
                .collect(Collectors.toList());
    }
}
class CarGenerator implements DataGenerator<Car>{
    private RandomString randomString = new RandomString();
    @Override
    public List<Car> generator(int count){
        return Stream.generate(()->{
                    String brand = randomString.generateString();
                    int productionYear = (int)(Math.random()*75)+1960;
                    double price = Math.random()*1000000+20000;
                    return new Car(brand,productionYear,price);
                }).limit(count)
                .collect(Collectors.toList());
    }
}
class DoubleGenerator implements DataGenerator<Double>{
    @Override
    public List<Double> generator(int count){
        return Stream.generate(()->Math.random()*Double.MAX_VALUE)
                .limit(count)
                .collect(Collectors.toList());
    }
}
class IntegerGenerator implements DataGenerator<Integer>{
    @Override
    public List<Integer> generator(int count){
        return Stream.generate(()->(int)(Math.random()*Integer.MAX_VALUE))
                .limit(count)
                .collect(Collectors.toList());
    }
}
class MyColorGenerator implements DataGenerator<MyColor>{
    @Override
    public List<MyColor> generator(int count){
        return Stream.generate(()->{
                    int r = (int)(Math.random()*256);
                    int g = (int)(Math.random()*256);
                    int b = (int)(Math.random()*256);
                    return new MyColor(r, g, b);
                }).limit(count)
                .collect(Collectors.toList());
    }
}

class PersonGenerator implements DataGenerator<Person>{
    private RandomString randomString = new RandomString();
    @Override
    public List<Person> generator(int count){
        return Stream.generate(()->{
                    String name = randomString.generateString();
                    int birthYear = (int)(Math.random()*85)+1940;
                    return new Person(name, birthYear);
                }).limit(count)
                .collect(Collectors.toList());
    }
}


interface TestExecutor<T>{
    TestResult test(Collection<T> collection, List<T> data);
}

class ReadAfterIndexTest<T> implements TestExecutor<T> {
    @Override
    public TestResult test(Collection<T> collection, List<T> data) {
        try {
            if (!(collection instanceof List)) {
                throw new IllegalArgumentException();
            }
        }catch (IllegalArgumentException e){
            throw new RuntimeException("Nieprawidłowy rodzaj kolekcji do wybranego testu");
        }
        List<T> list = (List<T>) collection;
        for (T element : data){
            list.add(element);
        }
        long addTime = 0;
        long searchTime;
        long removeTime = 0;
        int numberOfReads = data.size();
        long startTime = System.nanoTime();
        for(int i=0;i<numberOfReads;i++){
            int index = (int)(Math.random()*list.size());
            T element = list.get(index);
        }
        long endTime = System.nanoTime();
        searchTime = endTime - startTime;
        TestResult testResult= new TestResult(addTime, searchTime, removeTime);
        return testResult;
    }
}
class InsertAndRemoveTest<T> implements TestExecutor<T>{
    @Override
    public TestResult test(Collection<T> collection, List<T> data){
        long startTime;
        long endTime;
        long addTime;
        long searchTime = 0;
        long removeTime;
        startTime = System.nanoTime();
        for (T element : data) {
            collection.add(element);
        }
        endTime = System.nanoTime();
        addTime = endTime-startTime;

        startTime = System.nanoTime();
        for (T element : data){
            collection.remove(element);
        }
        endTime = System.nanoTime();
        removeTime = endTime-startTime;

        TestResult testResult = new TestResult(addTime,searchTime,removeTime);
        return testResult;

    }
}
class ExistenceOfAnElementTest<T> implements TestExecutor<T> {
    @Override
    public TestResult test(Collection<T> collection, List<T> list){
        long start;
        long end;
        long addTime = 0;
        long searchTime;
        long removeTime = 0;

        start = System.nanoTime();
        for (T element : list){
            collection.contains(element);
        }
        end = System.nanoTime();
        searchTime = end - start;
        TestResult testResult = new TestResult(addTime, searchTime, removeTime);
        return testResult;
    }
}
class ElementSearchTest<T> implements TestExecutor<T>{
    @Override
    public TestResult test(Collection<T> collection, List<T> data){
        long addTime = 0;
        long searchTime;
        long removeTime = 0;
        long startTime;
        long endTime;
        startTime = System.nanoTime();
        for (T element : data) {
            collection.contains(element);
        }
        endTime = System.nanoTime();
        searchTime = endTime-startTime;
        TestResult testResult = new TestResult(addTime, searchTime, removeTime);
        return testResult;
    }
}

interface TestResultPresenter {
    void result(TestResult result);
}

class ConsoleTestResult implements TestResultPresenter{
    @Override
    public void result(TestResult testResult){
        System.out.println(testResult.toString());
    }
}

class CsvTestResult implements TestResultPresenter{
    @Override
    public void result(TestResult testResult) {
        String file = "efficiency_test.csv";
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("Czas dodawania elementu w nanosekundach,Czas szukania elementu w nanosekundach,Czas usuwania elementu w nanosekundach\n");
            String line = testResult.getAddTime() + "," + testResult.getSearchTime() + "," + testResult.getRemoveTime();
            fileWriter.write(line);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException("Zapisanie do pliku CSV nie powiodło się");
        }
    }
}

class TestResult{
    private long addTime;
    private long searchTime;
    private long removeTime;
    public TestResult(long addTime, long searchTime, long removeTime){
        this.addTime = addTime;
        this.searchTime = searchTime;
        this.removeTime = removeTime;
    }
    public long getAddTime() {
        return addTime;
    }
    public long getSearchTime(){
        return searchTime;
    }
    public long getRemoveTime() {
        return removeTime;
    }
    @Override
    public String toString(){
        String addTimeResult = (addTime == 0) ? "Dodawanie nie występuje w tym teście" : "Czas dodawania w nanosekundach: " + addTime;
        String searchTimeResult = (searchTime == 0) ? "Wyszukiwanie nie występuje w tym teście" : "Czas wyszukiwania w nanosekundach: " + searchTime;
        String removeTimeResult = (removeTime == 0) ? "Usuwanie nie występuje w tym teście" : "Czas usuwania w nanosekundach: " + removeTime;
        return "Wyniki testu:\n" + addTimeResult + "\n" + searchTimeResult + "\n" + removeTimeResult;
    }
}