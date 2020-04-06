package com.xuxianda;

import java.util.*;

/**
 * Created by XiandaXu on 2020/4/3.
 */
public class CardGame {
    static Integer X = 4;//类型
    static Integer Y = 7;//编号
    static List<Card> initList = new ArrayList(); //初始化28张卡牌，分发完成之后剩下1张卡牌
    static List<Card> play0 = new ArrayList(); //玩家1
    static List<Card> play1 = new ArrayList(); //玩家2
    static List<Card> play2 = new ArrayList(); //玩家3
    static List<Card> putCards = new ArrayList<>();
    static List<List<Card>> hiddenListCard = new ArrayList<>(); //暗置的卡牌数组
    static Scanner scanner = new Scanner(System.in);
    static boolean flag = false; //游戏结束标志

    static {
        for (int i = 1; i <= X; i++) {
            for (int j = 1; j <= Y; j++) {
                initList.add(new Card(i, j));
            }
        }
    }

    /**
     * 发牌功能
     */
    public static void deliverCard() {
        int count = 0;
        int count1 = 0;
        int count2 = 0;
        Random random = new Random();
        while (true) {
            int i = random.nextInt(3);
            int j = random.nextInt(initList.size());
            switch (i) {
                case 0:
                    if (count < 9) {
                        play0.add(initList.get(j));
                        count++;
                        initList.remove(j);
                    }
                    break;
                case 1:
                    if (count1 < 9) {
                        play1.add(initList.get(j));
                        count1++;
                        initList.remove(j);
                    }
                    break;
                case 2:
                    if (count2 < 9) {
                        play2.add(initList.get(j));
                        count2++;
                        initList.remove(j);
                    }
                    break;
            }
            if (count + count1 + count2 == 27) {
                break;
            }
        }
        sortList(play0);
        sortList(play1);
        sortList(play2);
    }

    /**
     * 对卡牌进行排序
     *
     * @param list
     * @return
     */
    public static void sortList(List<Card> list) {
        Collections.sort(list, new Comparator<Card>() {
            public int compare(Card o1, Card o2) {
                if (o1.getType() == o2.getType()) {
                    return o1.getNo() - o2.getNo();
                } else {
                    return o1.getType() - o2.getType();
                }
            }
        });
    }

    /**
     * 从当前玩家的卡牌中找出对应的相同类型和编号的卡牌
     *
     * @param play
     * @param type
     * @param no
     * @return
     */
    public static Card getCardFromList(List<Card> play, Integer type, Integer no) {
        for (Card card : play) {
            if (card.getType().equals(type) && card.getNo().equals(no)) {
                return card;
            }
        }
        return null;
    }

    /**
     * 查看玩家手中是否有3张不同花色但是相同序号或者3张相同花色连续序号的卡牌
     *
     * @param play
     */
    public static List<List<Card>> sameNoOrTripleNo(List<Card> play, Integer type) {
        List<List<Card>> list = new ArrayList();
        //3张相同花色连续序号的卡牌
        int[] numArray = new int[8];
        play.stream().forEach(x -> {
            /**
             * 将所有此类型的卡牌的编号放入数组中
             */
            if (x.getType().equals(type)) {
                numArray[x.getNo()] = 1;
            }
        });
        for (int no = 1; no <= 5; no++) {
            if (numArray[no] == 1 && numArray[no + 1] == 1 && numArray[no + 2] == 1) {
                for (int j = 0; j < play.size() - 2; j++) {
                    Card card = play.get(j);
                    if (card.getType().equals(type) && card.getNo().equals(no)) {
                        List<Card> tripleNo = new ArrayList<>();
                        tripleNo.add(play.get(j));
                        tripleNo.add(play.get(j + 1));
                        tripleNo.add(play.get(j + 2));
                        list.add(tripleNo);
                        break;
                    }
                }
            }
        }
        //3张不同花色但是相同序号
        for (Card card : play) {
            if (card.getType().equals(type)) {
                int[] typeArray = new int[5];
                Integer no = card.getNo();
                for (Card tmp : play) {
                    if (tmp.getNo().equals(no)) {
                        typeArray[tmp.getType()] = 1;
                    }
                }
                if (typeArray[1] + typeArray[2] + typeArray[3] + typeArray[4] == 3) {
                    List<Card> tripleNo = new ArrayList<>();
                    for (int i = 1; i <= 4; i++) {
                        if (typeArray[i] == 1) {
                            tripleNo.add(getCardFromList(play, i, no));
                        }
                    }
                    list.add(tripleNo);
                } else if (typeArray[1] + typeArray[2] + typeArray[3] + typeArray[4] == 4) {
                    for (int i = 1; i <= 4; i++) {
                        if (i != type) {
                            for (int j = i + 1; j <= 4; j++) {
                                if (j != type) {
                                    List<Card> tripleNo = new ArrayList<>();
                                    tripleNo.add(getCardFromList(play, type, no));
                                    tripleNo.add(getCardFromList(play, i, no));
                                    tripleNo.add(getCardFromList(play, j, no));
                                    list.add(tripleNo);
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }


    public static List<Card> getSingleCard(List<Card> play, Integer type) {
        List<Card> putCard = new ArrayList<>();
        play.forEach(x -> {
            if (x.getType().equals(type)) {
                putCard.add(x);
            }
        });
        return putCard;
    }

    public static void putTripleCard(List<Card> play,Integer index,Integer guessX){
        List<List<Card>> sameNoOrTripleNo = sameNoOrTripleNo(play, guessX);
        if (sameNoOrTripleNo.size() > 0) {
            System.out.println("玩家" + index + "符合条件的卡牌如下\n" + sameNoOrTripleNo);
            System.out.println("玩家" + index + "输入需要出第几组卡牌:");
            Integer cardListIndex = scanner.nextInt();
            System.out.println("请输入需要暗置的卡牌");
            Integer hiddenIndex1 = scanner.nextInt(); //第一张暗置卡牌
            Integer hiddenIndex2 = scanner.nextInt(); //第二张暗置卡牌
            List<Card> cards = sameNoOrTripleNo.get(cardListIndex);
            cards.get(hiddenIndex1).setHidden(true);
            cards.get(hiddenIndex2).setHidden(true);
            cards.stream().forEach(x -> {
                play.remove(x);
            });
            hiddenListCard.add(cards);
        } else {
            List<Card> singleCard = getSingleCard(play1, guessX);
            System.out.println("玩家"+index+"符合条件的卡牌如下\n"+singleCard);
            if (singleCard.size() > 0) {
                System.out.println("请输入需要出牌的编号:");
                Integer singleIndex = scanner.nextInt();
                putCards.add(singleCard.get(singleIndex));
                play.remove(singleCard.get(singleIndex));
            } else {
                System.out.println("玩家"+index+"无卡牌可出");
            }
        }
    }

    /**
     * @param play             出牌玩家
     * @param sameNoOrTripleNo 三连号或者三个相同编号的卡牌数组
     * @param index            玩家编号
     */
    public static void putTripleCards(List<Card> play, List<List<Card>> sameNoOrTripleNo, Integer index) {

    }

    /**
     * 每个玩家开始轮流进行卡牌操作
     */
    public static void circlePlay() {
        int index = 0; //当前操作的玩家
        Integer guessX; //猜牌的类型
        Integer guessY; //猜牌的编号
        while (true) {
            System.out.println("请第" + index + "玩家输入需要操作编号（1：猜牌，2：请求卡牌，3：查阅卡牌）");
            int operation = scanner.nextInt();
            switch (operation) {
                case 1:
                    /**
                     * 玩家说出一张牌, 如果它是杀手牌, 则游戏结束
                     */
                    System.out.println("请输入类型和编号:");
                    guessX = scanner.nextInt();
                    guessY = scanner.nextInt();
                    Card card = initList.get(0);
                    if (card.getType().equals(guessX) && card.getNo().equals(guessY)) {
                        flag = true;
                    }
                    break;
                case 2:
                    /**
                     *  玩家向其它玩家请求卡牌和放置卡牌, 具
                     体来讲分为以下 3 步:
                     1) A1.1 玩家向其他两位玩家请求某个类型的牌.
                     2) A1.2 其他两位玩家如果手中有这种类型的牌,
                     则选取一张牌面向下交给当前玩家; 如果手中没有这
                     种类型的牌, 则告诉所有玩家.
                     3) A1.3 当前玩家根据手中的牌, 如果出现 3 张不
                     同花色但是相同序号, 或者 3 张相同花色并且连续序
                     号的手牌时, 需要将其中两张暗置, 只暴露其中一张来
                     进行出牌; 若手中没有符合上述情形的三张手牌时, 只
                     需出手牌中的任意一张, 无需暗置
                     */
                    System.out.println("请输入卡牌类型:");
                    guessX = scanner.nextInt();  //玩家请求一张卡牌
                    if (index == 0) {
                        putTripleCard(play1,1,guessX);
                        putTripleCard(play2,2,guessX);
                    } else if (index == 1) {
                        putTripleCard(play0,0,guessX);
                        putTripleCard(play2,2,guessX);
                    } else {
                        putTripleCard(play0,0,guessX);
                        putTripleCard(play1,1,guessX);
                    }
                    break;
                case 3:
                    System.out.println(hiddenListCard + "\n请输入想查阅的卡牌序号");
                    Integer hiddenIndex = scanner.nextInt();
                    int count = 0;
                    for (int i = 0; i < hiddenListCard.size() && count<=hiddenIndex; i++) {
                        List<Card> cards = hiddenListCard.get(i);
                        for (Card tmp : cards) {
                            if(count==hiddenIndex && tmp.isHidden()){
                                count++;
                                tmp.setHidden(false);
                                break;
                            }
                        }
                    }
                    System.out.println(hiddenListCard + "\n请输入想查阅的卡牌序号");
            }
            if (flag) {
                System.out.println("游戏结束");
                break;
            }
            index++;
        }
    }

    public static void main(String[] args) {
        deliverCard();
        circlePlay();
    }

}
