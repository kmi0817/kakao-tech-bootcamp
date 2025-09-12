import java.util.*;

public class Main {
    private static final int TEN_SECONDS = 10000;
    private static final int FIVE_SECONDS = 5000;

    private static int id = 1;
    private static Hero hero;
    private static Healer healer;
    private static List<Villain> villains = new ArrayList<>();

    public static void main(String[] args) {
        initialize();

        System.out.println("============== [히어로 vs 빌런] 게임을 시작합니다 ==============");
        System.out.println("사용자의 공격 여부와 관계 없이 모든 빌런은 5초 간격으로 공격하고,");
        System.out.println("추가로 Boss 빌런은 10초마다 광역 공객합니다.");
        System.out.println("시간을 끌수록 죽을 확률이 높아집니다. 재빠르게 공격하세요!");

        Scanner sc = new Scanner(System.in);

        while (!isOver()) {
            int direction;

            if (!hero.isDead() && !healer.isDead()) {
                System.out.println();
                System.out.println("- 누구를 선택하시겠습니까?");
                System.out.println("(1) Hero\t(2)Healer");

                direction = Integer.parseInt(sc.nextLine());
            } else if (hero.isDead()) {
                System.out.println("Healer가 죽어 자동으로 Hero가 선택됐습니다.");
                direction = 2;
            } else {
                System.out.println("Hero가 죽어 자동으로 Healer가 선택됐습니다.");
                direction = 1;
            }

            if (direction == 1) {
                askTarget();

                int target = Integer.parseInt(sc.nextLine()) - 1;

                Villain villain = villains.get(target);
                hero.attack(villain);

                if (villain.isDead()) {
                    villains.remove(villain);
                }
            } else if (direction == 2) {
                System.out.println("- 어떤 행위를 하시겠습니까?");
                System.out.println("(1) 공격\t(2) Hero 살리기");

                int input = Integer.parseInt(sc.nextLine());

                if (input == 1) {
                    askTarget();

                    int target = Integer.parseInt(sc.nextLine()) - 1;

                    Villain villain = villains.get(target);
                    healer.attack(villain);

                    if (villain.isDead()) {
                        villains.remove(villain);
                    }
                } else if (input == 2) {
                    healer.reviveHero(hero);
                } else {
                    System.out.println("** 잘못된 입력값입니다."); // 다시 입력 받아야 하는데
                }
            } else {
                System.out.println("** 잘못된 입력값입니다.");
            }
        }

        String winner = villains.isEmpty() ? "착한 팀" : "나쁜 팀";
        System.out.println("\n======================= 게임 종료 =======================");
        System.out.println(winner + "이 이겼습니다!");
    }

    private static void initialize() {
        hero = new Hero(id++, 100, 20, 5);
        healer = new Healer(id++, 80, 5, 8);

        Boss boss = new Boss(id++, 100, 15);
        villains.add(boss);
        villains.add(new Villain(id++, 20, 15, 5));
        villains.add(new Villain(id++, 30, 10, 4));
        villains.add(new Villain(id++, 40, 5, 6));
        villains.add(new Villain(id++, 10, 10, 10));
        villains.add(new Villain(id++, 50, 20, 7));

        startAttackThreads();
        startAttackAllThread(boss);
    }

    private static void startAttackThreads() {
        for (Villain v : villains) {
            Thread attackThread = new Thread(() -> {
                while (!v.isDead() && v.getAttackCount() < v.getAttackLimit()) {
                    try {
                        Thread.sleep(FIVE_SECONDS);
                        List<Hero> targets = getAliveHeroes();

                        Random random = new Random();
                        int index = random.nextInt(targets.size());
                        Hero target = targets.get(index);
                        v.attack(target);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            attackThread.start();
        }
    }

    private static void startAttackAllThread(Boss boss) {
        Thread attackAllThread = new Thread(() -> {
            try {
                List<Hero> targets = getAliveHeroes();

                while (!targets.isEmpty()) {
                    Thread.sleep(TEN_SECONDS);
                    boss.attackAll(targets);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        attackAllThread.start();
    }

    private static boolean isOver() {
        return (hero.isDead() && healer.isDead()) || villains.isEmpty();
    }

    private static void askTarget() {
        System.out.println("- 다음 중 누구를 공격하시겠습니까?");

        for (int i = 0; i < villains.size(); i++) {
            System.out.print("(" + (i + 1) + ") " + villains.get(i).getName() + "\t");
        }

        System.out.println();
    }

    private static List<Hero> getAliveHeroes() {
        List<Hero> targets = new ArrayList<>();

        if (!hero.isDead()) {
            targets.add(hero);
        }

        if (!healer.isDead()) {
            targets.add(healer);
        }
        return targets;
    }
}