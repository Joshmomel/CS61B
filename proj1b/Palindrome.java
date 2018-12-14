
public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        ArrayDeque<Character> arrayDeque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            Character theChar = word.charAt(i);
            arrayDeque.addLast(theChar);
        }

        return arrayDeque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> characterDeque = wordToDeque(word);
        char front;
        char back;

        while (characterDeque.size() > 1) {
            front = characterDeque.removeFirst();
            back = characterDeque.removeLast();
            if (front != back) {
                return false;
            }
        }

        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> characterDeque = wordToDeque(word);
        char front;
        char back;

        while(characterDeque.size() > 1){
            front = characterDeque.removeFirst();
            back = characterDeque.removeLast();
            if (!cc.equalChars(front, back)){
                return false;
            }
        }
        return true;
    }

}
