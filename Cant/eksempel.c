#include<stdio.h>
 int main() {
int size = 3;
int pos = 4;
int fin = 7;
int plane[] = {0,0,0,0,0,0,0,0,0};
int dir = 0;
int del1[] = {1};
int del2[] = {7};
int tele1[] = {2,0};
int tele2[] = {7,7};
int ind[] = {1};
int out[] = {7};
while (pos != fin) {
    // move the ant
    if (dir == 0)      { pos -= size; } // up
    else if (dir == 1) { pos += 1; }    // left
    else if (dir == 2) { pos += size; } // down
    else               { pos -= 1; }    // right

    // check for inputceller
    int i = 0;
    for(i; i < sizeof(ind) / sizeof(int); i++) {
      if (ind[i] == pos) {
        printf("input on %d: ", ind[i]);
        int input;
        scanf("%d", &input);
        if(input >= 1)
          plane[pos] = 1;
        else
          plane[pos] = 0;
      }
    }

    // change celle state and direction
    if (plane[pos] >= 1) {
      plane[pos] = 0;
      dir = (dir - 1) % 4;
    }
    else {
      plane[pos] = 1;
      dir = (dir + 1) % 4;
    }

    // check for delceller
    i = 0;
    for(i; i < sizeof(del1) / sizeof(int); i++) {
      if (del1[i] == pos) {
        plane[del2[i]] = plane[pos];
      }
    }

    // check for teleceller
    i = 0;
    for(i; i < sizeof(tele1) / sizeof(int); i++) {
      if (tele1[i] == pos) {
        pos = tele2[i];
      }
    }

    // check for outputceller
    i = 0;
    for(i; i < sizeof(out) / sizeof(int); i++) {
      if (out[i] == pos) {
        printf("%d: ", pos);
        printf("%d\n", plane[pos]);
      }
    }
  }
  return plane[pos];
}

