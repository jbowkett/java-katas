chop(lst, toFind) =
  if  halfwayValue == toFind
  then
    halfwayIndex
  else
    if length(lst) == 1
      then
        error "Not found in list."
    else
      if halfwayValue < toFind
      then
        halfwayIndex + chopLatterHalf
      else
        chopFirstHalf
  where
  halfwayIndex = length(lst) `div` 2
  halfwayValue = lst !! halfwayIndex
  latterHalfOfList = drop halfwayIndex lst
  firstHalfOfList  = take halfwayIndex lst
  chopLatterHalf = chop(latterHalfOfList, toFind)
  chopFirstHalf = chop(firstHalfOfList, toFind)



chop2 :: (Ord a) => ([a], a) -> Int
chop2 (lst, x)
   | halfwayValue == x = halfwayIndex
   | listLength   == 1 = error "Not found in list."
   | halfwayValue  < x = halfwayIndex + indexInLaterHalf
   | otherwise         = indexInFirstHalf
  where
  listLength   = length(lst)
  halfwayIndex = listLength `div` 2
  halfwayValue = lst!! halfwayIndex
  latterHalfOfList = drop halfwayIndex lst
  firstHalfOfList  = take halfwayIndex lst
  indexInLaterHalf = chop2(latterHalfOfList, x)
  indexInFirstHalf = chop2(firstHalfOfList, x)
