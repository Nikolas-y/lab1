package lab1.var4; // Определяет пакет, в котором находится данный класс.

import java.lang.reflect.Constructor; // Импортируется класс для работы с конструкторами через рефлексию.
import java.util.Arrays; // Импортируется для работы с массивами.
import java.util.Comparator; // Импортируется для реализации сравнения объектов.
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.HashMap;
public class Main {
    public Main() {} // Пустой конструктор класса Main.

    public static void main(String[] args) throws Exception { // Основной метод программы с возможностью выброса исключений.
        Food[] breakfast = new Food[20]; // Инициализация массива объектов Food для завтрака.

        int calorieValue = 0; // Переменная для хранения общей калорийности.
        int itemsSoFar = 0; // Счетчик товаров, добавленных в массив.

        boolean isCaloriesCalculate = false; // Флаг для проверки, нужно ли рассчитывать калории.
        boolean isNeedToSort = false; // Флаг для проверки, нужно ли сортировать еду.

        for (String arg: args) { // Проход по каждому аргументу, переданному в командной строке.
            String[] parts = arg.split("/"); // Разделение аргумента на части с учетом символа '/'.

            // Проверка, если аргумент содержит ключ "-calories".
            if(parts[0].equals("-calories")){
                isCaloriesCalculate = true; // Устанавливаем флаг для расчета калорий.
                continue; // Переходим к следующему аргументу.
            }
            // Проверка, если аргумент содержит ключ "-sort".
            if(parts[0].equals("-sort")){
                isNeedToSort = true; // Устанавливаем флаг для сортировки.
                continue; // Переходим к следующему аргументу.
            }

            Class myClass; // Объявление переменной для хранения класса.

            try {
                // Получаем класс по имени из пакета, к которому относится объект еды.
                myClass = Class.forName("lab1.var4." + parts[0]);
                //System.out.println(MyClass);
            }
            catch (ClassNotFoundException e) {
                System.out.println("Извините, продукта '" + parts[0] + "' нет в рационе завтрака, он будет пропущен.");
                continue; // Если класс не найден, выводим сообщение и переходим к следующему аргументу.
            }

            try {
                // Проверка длины parts для определения, сколько параметров передано конструктору.
                if (parts.length == 1) {
                    // Если дополнительных параметров нет, создаем объект с помощью конструктора без параметров.
                    Constructor constructor = myClass.getConstructor();
                    breakfast[itemsSoFar] = (Food) constructor.newInstance();
                } else if (parts.length == 2) {
                    // Если один параметр, используем конструктор с одним параметром.
                    Constructor constructor = myClass.getConstructor(String.class);
                    breakfast[itemsSoFar] = (Food) constructor.newInstance(parts[1]);
                } else if (parts.length == 3) {
                    // Если два параметра, используем конструктор с двумя параметрами.
                    Constructor constructor = myClass.getConstructor(String.class, String.class);
                    breakfast[itemsSoFar] = (Food) constructor.newInstance(parts[1], parts[2]);
                }
            }
            catch (NoSuchMethodException e) {
                System.out.println("Извините, продукта '" + parts[0] + "' с такими параметрами нет в рационе завтрака, он будет пропущен.");
                continue; // Если подходящий конструктор не найден, выводим сообщение и переходим к следующему аргументу.
            }

            calorieValue += breakfast[itemsSoFar].calculateCalories(); // Добавляем калории продукта к общей калорийности.
            itemsSoFar++; // Увеличиваем счетчик продуктов.
        }

        Food f = breakfast[0]; // Сохраняем первый элемент массива для будущего использования.

        if(isNeedToSort) // Если нужно сортировать продукты.
            Arrays.sort(breakfast, new Comparator<Food>() {
                @Override
                public int compare(Food o1, Food o2) {
                    if(o1 == null) return 1; // Если o1 - null, помещаем его в конец.
                    if(o2 == null) return -1; // Если o2 - null, помещаем o2 в конец.
                    int c1 = o1.getName().length(); // Получаем калории первого продукта.
                    int c2 = o2.getName().length(); // Получаем калории второго продукта.
                    return Integer.compare(c2, c1); // Сравниваем калории для сортировки по убыванию.
                }
            });

        for (Food item: breakfast) // Проходим по всем продуктам для их потребления.
            if (item != null)
                item.consume(); // Вызываем метод consume для каждого продукта, который не равен null.
            else
                break; // Если нашли null, прерываем цикл.

        if(isCaloriesCalculate) {
            System.out.println("Общая калорийность: " + calorieValue); // Выводим общую калорийность продуктов, если запрошено.
        }

//        Map <String, Integer> uni = new HashMap<String,Integer>() ;
//
//        for(Food f : breakfast) {
//            if(f != null) {
//                //f.toString();
//                uni.put(f.toString(), uni.getOrDefault(f.toString(), 0) + 1);
//            }
//            //System.out.println("Количество продуктов как '" + f.toString() + "': " + countOfFood(f, t)); // Считаем и выводим количество продуктов такого же типа.
//        }
        System.out.println("Количество продуктов как '" + f.toString() + "': " + countOfFood(f, breakfast));
//        System.out.println(uni);
        System.out.println("Всего хорошего!"); // Прощальное сообщение.
    }

    public static int countOfFood(Food f, Food[] breakfast) { // Метод для подсчета количества одинаковых продуктов в массиве.
        int count = 0; // Переменная для хранения количества.
        for(Food item: breakfast) { // Проходим по массиву продуктов.
            if(item != null && item.equals(f)) { // Если продукт не null и равен переданному.
                count++;
                // Увеличиваем счетчик.
            }
        }
        return count; // Возвращаем количество найденных продуктов.
    }
}