import java.util.Random;

public class Main {
    private static final int NUM_PROCESSORS = 4; // Кількість доступних процесорів
    private static final int NUM_PROCESSES = 100; // Кількість процесів для обробки

    public static void main(String[] args) {
        int[] processors = new int[NUM_PROCESSORS]; // Масив, що відображає кількість процесів, оброблених кожним процесором
        int totalProcessed = 0;

        Random random = new Random(); // Генератор випадкових чисел

        for (int i = 0; i < NUM_PROCESSES; i++) {
            int processorIndex = getNextAvailableProcessor(processors); // Отримання індексу найменш зайнятого процесора
            processors[processorIndex]++;
            totalProcessed++;

            final int processId = i + 1; // Ідентифікатор поточного процесу

            // Створення потоку для обробки процесу
            Thread processThread = new Thread(() -> {
                // Імітація часу обробки
                int processingTime = random.nextInt(1000);
                try {
                    Thread.sleep(processingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Процес " + processId + " завершено на процесорі " + (processorIndex + 1));
            });

            processThread.start(); // Запуск потоку для обробки процесу
        }

        // Очікування завершення всіх потоків
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Кількість задіяних процесорів: " + getEngagedProcessors(processors));
        System.out.println("Відсоток процесів, оброблених кожним процесором:");
        for (int i = 0; i < NUM_PROCESSORS; i++) {
            double percent = (double) processors[i] / totalProcessed * 100;
            System.out.println("Процесор " + (i + 1) + ": " + percent + "%");
        }
    }

    // Метод для отримання індексу найменш зайнятого процесора
    private static int getNextAvailableProcessor(int[] processors) {
        int minIndex = 0;
        int minValue = processors[0];

        for (int i = 1; i < processors.length; i++) {
            if (processors[i] < minValue) {
                minIndex = i;
                minValue = processors[i];
            }
        }

        return minIndex;
    }

    // Метод для підрахунку кількості задіяних процесорів
    private static int getEngagedProcessors(int[] processors) {
        int count = 0;
        for (int i = 0; i < processors.length; i++) {
            if (processors[i] > 0) {
                count++;
            }
        }
        return count;
    }
}
