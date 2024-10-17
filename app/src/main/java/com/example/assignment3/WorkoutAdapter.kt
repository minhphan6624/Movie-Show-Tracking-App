import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3.databinding.ItemWorkoutBinding
import com.example.assignment3.data.Workout

class WorkoutAdapter : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    private var workoutList = emptyList<Workout>()

    // ViewHolder class
    class WorkoutViewHolder(private val binding: ItemWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout) {
            binding.workoutName.text = workout.name
            binding.workoutDate.text = workout.date
            binding.workoutDuration.text = workout.duration
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val binding = ItemWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val workout = workoutList[position]
        holder.bind(workout)
    }

    override fun getItemCount(): Int = workoutList.size

    fun setWorkouts(workouts: List<Workout>) {
        this.workoutList = workouts
        notifyDataSetChanged()
    }
}
