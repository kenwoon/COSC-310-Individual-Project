class Transaction
{
    private String date;
    private String name;
    private int quantity;

    public Transaction(String date, String name, int quantity)
    {
        this.date = date;
        this.name = name;
        this.quantity = quantity;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public String[] toStringArray()
    {
        return new String[]
        {
            date,
            name,
            String.format("%d", quantity)
        };
    }

    @Override
    public String toString()
    {
        return "Transaction [date = " + date + ", name = " + name + ", quantity = " + quantity + "]";
    }
}