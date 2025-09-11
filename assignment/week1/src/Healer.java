class Healer extends Hero {
    Healer(int id, int hp, int power, int lives) {
        super(id, hp, power, lives);
        setName("Healer" + id);
    }

    void reviveHero(Hero hero) {
        try {
            if (!hero.isDead()) {
                throw new RuntimeException(hero.getName() + "이/가 살아 있으므로 환생시킬 수 없습니다.");
            }

            if (isDead()) {
                throw new RuntimeException(getName() + "은/는 죽었기 때문에 히어로를 환생시킬 수 없습니다.");
            }

            if (getLives() < 2) {
                throw new RuntimeException(getName() + "의 목숨을 나눠주면 죽기 때문에 히어로를 환생시킬 수 없습니다.");
            }

            hero.setHp(100);
            hero.incrementLife();
            decrementLife();

            System.out.printf("%s이/가 목숨 1개를 사용해 %s을/를 환생시켰습니다.%n", getName(), hero.getName());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
