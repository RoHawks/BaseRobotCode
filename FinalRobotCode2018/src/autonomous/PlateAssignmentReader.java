package autonomous;

import edu.wpi.first.wpilibj.DriverStation;

public class PlateAssignmentReader 
{
	public static char GetNearSwitchSide()
	{
		return GetGameSpecficData(0);
	}
	
	public static char GetScaleSide()
	{
		return GetGameSpecficData(1);
	}
	
	public static char GetFarSwitchSide()
	{
		return GetGameSpecficData(2);
	}
	
	private static char GetGameSpecficData(int pChar)
	{
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData == null)
		{
			return 'U';
		}
		else if(gameData.length() > pChar)
        {
		  return gameData.charAt(pChar);
        }
		else
		{
			return 'U';//unknown?
		}
	}

}
