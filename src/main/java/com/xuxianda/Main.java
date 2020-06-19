package com.xuxianda;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiandaXu on 2020/5/23.
 */
class Position {
    private Integer x;
    private Integer y;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Integer n = new Integer(bufferedReader.readLine());
        String qqPosition = bufferedReader.readLine();
        List<Position> qqList = new ArrayList<>();
        Integer m = new Integer(bufferedReader.readLine());
        String ccPosition = bufferedReader.readLine();
        List<Position> ccList = new ArrayList<>();
        int qq[][] = new int[9][9];
        int cc[][] = new int[9][9];
        String[] qqSplits = qqPosition.split(" ");
        for (String temp : qqSplits) {
            char[] chars = temp.toCharArray();
            qq[chars[0] - 'A' + 1][chars[1] - '0'] = 1;
            qqList.add(new Position(chars[0] - 'A' + 1, chars[1] - '0'));
        }
        String[] ccSplits = ccPosition.split(" ");
        for (String temp : ccSplits) {
            char[] chars = temp.toCharArray();
            cc[chars[0] - 'A' + 1][chars[1] - '0'] = 1;
            ccList.add(new Position(chars[0] - 'A' + 1, chars[1] - '0'));
        }

        while (true) {
            List<Position> removePosition = new ArrayList<>();
            int initialMax = 0;
            for (int i = 0; i < qqList.size(); i++) {
                Position position = qqList.get(0);
                int initX = position.getX();
                int initY = position.getY();
                int max = 1;
                List<Position> maxPosition = new ArrayList<>();
                maxPosition.add(position);
                for (int j = i + 1; j < qqList.size(); j++) {
                    int secondX = qqList.get(j).getX();
                    int secondY = qqList.get(j).getY();
                    int minusX = secondX - initX;
                    int minusY = secondY - initY;
                    while (secondX >= 1 && secondX <= 8 && secondY >= 1 && secondY <= 8) {
                        if (qq[secondX][secondY] == 1) {
                            for (int k = j; k < qqList.size(); k++) {
                                if (qqList.get(k).getX() == secondX && qqList.get(k).getY() == secondY) {
                                    maxPosition.add(qqList.get(k));
                                    break;
                                }
                            }
                            max++;
                        }
                        secondX += minusX;
                        secondY += minusY;
                    }
                    if (max > initialMax) {
                        initialMax = max;
                        removePosition.clear();
                        removePosition.addAll(maxPosition);
                    }
                    max =1;
                }
            }

            for(Position position : removePosition){
                qq[position.getX()][position.getY()] = 0;
            }
            n-=removePosition.size();
            removePosition.clear();
            if(n == 0){
                System.out.println("Cuber QQ");
                break;
            }

            initialMax = 0;
            for (int i = 0; i < ccList.size(); i++) {
                Position position = ccList.get(0);
                int initX = position.getX();
                int initY = position.getY();
                int max = 1;
                List<Position> maxPosition = new ArrayList<>();
                maxPosition.add(position);
                for (int j = i + 1; j < ccList.size(); j++) {
                    int secondX = ccList.get(j).getX();
                    int secondY = ccList.get(j).getY();
                    int minusX = secondX - initX;
                    int minusY = secondY - initY;
                    while (secondX >= 1 && secondX <= 8 && secondY >= 1 && secondY <= 8) {
                        if (cc[secondX][secondY] == 1) {
                            for (int k = j; k < ccList.size(); k++) {
                                if (ccList.get(k).getX() == secondX && ccList.get(k).getY() == secondY) {
                                    maxPosition.add(ccList.get(k));
                                    break;
                                }
                            }
                            max++;
                        }
                        secondX += minusX;
                        secondY += minusY;
                    }
                    if (max > initialMax) {
                        initialMax = max;
                        removePosition.clear();
                        removePosition.addAll(maxPosition);
                    }
                    max = 1;
                }

            }

            for(Position position : removePosition){
                cc[position.getX()][position.getY()] = 0;
            }
            m-=removePosition.size();

            if(m == 0){
                System.out.println("Quber CC");
                break;
            }
        }

    }

}
