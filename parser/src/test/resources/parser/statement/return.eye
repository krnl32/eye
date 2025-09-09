function int32_t test(int32_t args = 15)
{
	return 87;
}

function float32_t test2(int32_t arg)
{
	return 12.15f;
}

function float32_t add(const uint32_t x, float64_t y = a, str8_t z)
{
	return 10 + x + y + z;
}

function bool8_t defaultValue(bool8_t def = false)
{
	return false;
}

function void nothing()
{
	return;
}

function int32_t func(int32_t x, float64_t y = 12.55, const uint32_t z = 55)
{
	x = y;
	return x;
}

function int32_t main()
{
    while (true)
    {
    	return 3;
    }

    if (false)
    {
    	if (x == 5) {
    		return 1;
    	}
    }
}
