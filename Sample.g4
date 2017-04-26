grammar Sample;

/* Comment */

/*<>*/ @header { package org.test; }
/* <csharp> @header { using System.Linq; } */
/*<>*/ @members { private boolean isFalse = false; }
/*
    <cpp> @members { private bool isFalse = false; }
    <csharp>
*/

parse /*<>*/ /*<csharp,cpp> [int integer = 0, float decimal = 0] */: EOF
{
    int id = 0;
}
;