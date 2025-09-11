import java.util.*;

public class Main {
    private static int id = 1;
    private static int round = 0;
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
                System.out.println("누구를 선택하시겠습니까?");
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

                System.out.print("Hero가 " + hero.getPower() + "만큼 공격하여 " + villain.getName());

                if (villain.isDead()) {
                    villains.remove(villain);
                    System.out.println("이/가 죽었습니다.");
                } else {
                    System.out.println("의 hp가 " + villain.getHp() + "로 감소했습니다.");
                }
            } else if (direction == 2) {
                System.out.println("어떤 행위를 하시겠습니까?");
                System.out.println("(1) 공격\t(2) Hero 살리기");

                int input = Integer.parseInt(sc.nextLine());

                if (input == 1) {
                    askTarget();

                    int target = Integer.parseInt(sc.nextLine()) - 1;

                    Villain villain = villains.get(target);
                    healer.attack(villain);

                    System.out.print("Healer가 " + healer.getPower() + "만큼 공격하여 " + villain.getName());

                    if (villain.isDead()) {
                        villains.remove(villain);
                        System.out.println("이/가 죽었습니다.");
                    } else {
                        System.out.println("의 hp가 " + villain.getHp() + "로 감소했습니다.");
                    }
                } else if (input == 2) {
                    try {
                        healer.reviveHero(hero);
                        System.out.println("Healer가 목숨 1개를 사용해 Hero를 환생시켰습니다.");
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("** 잘못된 입력값입니다."); // 다시 입력 받아야 하는데
                }
            } else {
                System.out.println("** 잘못된 입력값입니다.");
            }
        }
    }

    private static void initialize() {
        hero = new Hero(id++, 100, 10, 5);
        healer = new Healer(id++, 80, 5, 8);

        Boss boss = new Boss(id++, 100, 15);
        villains.add(boss);
        villains.add(new Villain(id++, 20, 3, 5));
        villains.add(new Villain(id++, 30, 6, 4));
        villains.add(new Villain(id++, 40, 4, 6));
        villains.add(new Villain(id++, 10, 1, 10));
        villains.add(new Villain(id++, 50, 2, 7));

        startAttackThreads();
        startAttackAllThread(boss);
    }

    private static void startAttackThreads() {
        for (Villain v : villains) {
            Thread attackThread = new Thread(() -> {
                while (!v.isDead() && v.getAttackCount() < v.getAttackLimit()) {
                    try {
                        Thread.sleep(5000);
                        List<Hero> targets = new ArrayList<>();

                        if (!hero.isDead()) {
                            targets.add(hero);
                        }

                        if (!healer.isDead()) {
                            targets.add(healer);
                        }

                        Random random = new Random();
                        int index = random.nextInt(targets.size());
                        Hero target = targets.get(index);
                        v.attack(target);

                        System.out.println("============== " + v.getName() + "이/가 " + target.getName() + "을/를 공격했습니다.");
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
                Thread.sleep(10000);
                boss.attackAll(List.of(hero, healer));
                System.out.println("*************** " + boss.getName() + "이/가 전방 공격을 가했습니다.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        attackAllThread.start();
    }

    private static boolean isOver() {
        return (hero.isDead() && healer.isDead()) || villains.size() == 0;
    }

    private static void askTarget() {
        System.out.println("다음 중 누구를 공격하시겠습니까?");
        for (int i = 0; i < villains.size(); i++) {
            System.out.print("(" + (i + 1) + ") " + villains.get(i).getName() + "\t");
        }
        System.out.println();
    }

    private static void printCurrentStatus() {
        System.out.println("========================= 스코어 =========================");

        System.out.println("[착한 쪽]");
        System.out.println(hero);
        System.out.println(healer);

        System.out.println("[나쁜 쪽]");
        for (int i = 0; i < villains.size(); i++) {
            System.out.println(villains.get(i));
        }
        System.out.println("=========================================================");
    }
}