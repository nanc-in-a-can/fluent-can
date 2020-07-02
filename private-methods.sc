PrFluentCan {
    *zipMaxWith {|fn, arr1, arr2|
        ^max(arr1.size, arr2.size).collect({|i|
            fn.(arr1[i], arr2[i]);
        });
    }

    *validateElementForComposition {|el|
        ^case(
            {el.isFunction}, {el},
            {el.isNumber}, {_+el},
            {{|x| x}}
        )
    }

    *composeFnArrays {|fArr, gArr|
        ^PrFluentCan.zipMaxWith(
            {|fn1, fn2|
                PrFluentCan.validateElementForComposition(fn2)
                <> PrFluentCan.validateElementForComposition(fn1)
            },
            fArr,
            gArr,
        )
    }
}

/*PrFluentCan.composeFnArrays([1,_*2,3,4], [_+1, _+2])[1].(2)*/
