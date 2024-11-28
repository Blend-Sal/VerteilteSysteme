package inout;

import list.List;

public class ScriptWriter implements Output {
    private List<String> list = List.list();


    @Override
    public void print(String s) {
        list = list.cons(s);
    }

    @Override
    public void printLine(String s) {
        print(s);
    }


    @Override
    public void shutdownOutput() {
    }

    public List<String> toList() {
        return list;
    }
}