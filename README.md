# Huffman and LZW Compression

Two lossless compression algorithms implemented from scratch in Java. Each program reads a text
file and reports the original size in bits, the compressed size, the compression ratio and the
elapsed time.

## Results

Ratio is compressed bits ÷ original bits, so lower is better.

| Input | Size (bits) | Huffman ratio | Huffman time | LZW ratio | LZW time |
|---|---|---|---|---|---|
| test1.txt | 120 | 0.2750 | 49 ms | 0.6000 | 53 ms |
| test2.txt | 128,176 | 0.6082 | 64 ms | 0.4700 | 65 ms |
| test3.txt | 15,344 | 0.6040 | 58 ms | 0.6149 | 60 ms |
| small.txt | 48,568 | 0.5684 | 96 ms | 0.5362 | 68 ms |
| medium.txt | 7,096,792 | 0.5664 | 190 ms | 0.3692 | 395 ms |
| large.txt | 25,632,616 | 0.5617 | 337 ms | 0.3573 | 1160 ms |

- **LZW improves with file size, Huffman doesn't.** Huffman stays near 0.56 because it is bounded by
  per-character entropy. LZW keeps finding longer repeated sequences, improving from 0.54 to 0.36.
- **LZW is slower at scale** (1160 ms vs 337 ms on large.txt). Huffman processes one line at a time
  and only needs a frequency table; LZW builds the whole file into a single string in memory before
  matching prefixes. That's a consequence of the input handling, not of the algorithm.

## Implementation

**Huffman** — frequencies counted per ASCII character, then the two lowest-weight nodes combined
repeatedly until one root remains.
- Min-heap is a `PriorityQueue<HuffNode>` ordered by weight: O(n log n) construction for n distinct
  characters.
- Compressed size is the weighted path length, computed recursively. Summing WPL avoids building an
  explicit code string per character, which saves memory on large inputs.

**LZW** — a trie seeded with the 128 ASCII characters; the longest known prefix is matched, then that
prefix plus the next character is inserted as a new entry.
- Each node holds a `children` array indexed by character rather than a child-and-sibling pair, making
  next-character lookup O(1) instead of a walk along a sibling list.
- Codeword width starts at 8 bits and increases when the dictionary fills, with the threshold doubling
  by bitwise left shift.

## Running

```bash
cd huffman
javac *.java
java Main input.txt
```

Same for `lzw`.

## Layout

```
huffman/   Main.java, HuffNode.java
lzw/       Main.java, Node.java, Trie.java
```
