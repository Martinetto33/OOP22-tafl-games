package taflgames.model.pieces.code;

import java.util.Optional;

import taflgames.common.Player;
import taflgames.model.pieces.api.Team;
/**
 * DA ELIMINARE ALLA FINE
*/
public class ImplTeam implements Team{

    private Optional<String> namePlayer;
    private Player player;

    public ImplTeam(Player p, Optional<String> name) {
        this.player=p;
        if(name.isPresent()) {
            this.namePlayer= Optional.of(name.get());
        }
        else {
            this.namePlayer = Optional.empty();
        }
    }

    @Override
    public Optional<String> getNameOfUserPlayer() {
        return this.namePlayer;
    }

    @Override
    public Player getPlayerTeam() {
        return this.player;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((namePlayer == null) ? 0 : namePlayer.hashCode());
        result = prime * result + ((player == null) ? 0 : player.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ImplTeam other = (ImplTeam) obj;
        if (namePlayer == null) {
            if (other.namePlayer != null)
                return false;
        } else if (!namePlayer.equals(other.namePlayer))
            return false;
        if (player != other.player)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ImplTeam [namePlayer=" + namePlayer + ", player=" + player + "]";
    }

    
    
}
