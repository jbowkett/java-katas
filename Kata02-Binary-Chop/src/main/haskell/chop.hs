chop(lst, toFind) =
  if  halfwayValue == toFind
  then
    halfwayIndex
  else
    if length(lst) == 1
      then
        error "{toFind} Not found in list."
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
