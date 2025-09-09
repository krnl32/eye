function int32_t main()
{
	while (true)
    {
    	if (x == 2)
    		break;

    	if (x == 4)
    		continue;

    	x++;
    }

    for (x = 0; x < 5; x++)
    	if (x == 3)
    		break;

    do
    {
    	break;
    } while (true);
}
