import java.util.List;

class Boss extends Villain {
    private static int LIMIT = 99999999;

    Boss(int id, int hp, int power) {
        super(id, hp, power, LIMIT);
        setName("보스" + id);
    }

    void attackAll(List<Hero> heroes) {
        try {
            if (isDead()) {
                throw new RuntimeException(getName() + "은/는 죽었기 때문에 다른 캐릭터를 공격할 수 없습니다.");
            }

            int attackPower = getPower() / heroes.size();

            for (Hero hero : heroes) {
                hero.setHp(hero.getHp() - attackPower);
            }

            System.out.println("### " + getName() + "이/가 전방 공격을 가했습니다.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
