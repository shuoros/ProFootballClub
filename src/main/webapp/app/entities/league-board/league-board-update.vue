<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.leagueBoard.home.createOrEditLabel"
          data-cy="LeagueBoardCreateUpdateHeading"
          v-text="$t('proFootballClubApp.leagueBoard.home.createOrEditLabel')"
        >
          Create or edit a LeagueBoard
        </h2>
        <div>
          <div class="form-group" v-if="leagueBoard.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="leagueBoard.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.leagueBoard.position')" for="league-board-position"
              >Position</label
            >
            <input
              type="number"
              class="form-control"
              name="position"
              id="league-board-position"
              data-cy="position"
              :class="{ valid: !$v.leagueBoard.position.$invalid, invalid: $v.leagueBoard.position.$invalid }"
              v-model.number="$v.leagueBoard.position.$model"
              required
            />
            <div v-if="$v.leagueBoard.position.$anyDirty && $v.leagueBoard.position.$invalid">
              <small class="form-text text-danger" v-if="!$v.leagueBoard.position.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.leagueBoard.position.min" v-text="$t('entity.validation.min', { min: 1 })">
                This field should be at least 1.
              </small>
              <small class="form-text text-danger" v-if="!$v.leagueBoard.position.max" v-text="$t('entity.validation.max', { max: 8 })">
                This field cannot be longer than 8 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.leagueBoard.position.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.leagueBoard.win')" for="league-board-win">Win</label>
            <input
              type="number"
              class="form-control"
              name="win"
              id="league-board-win"
              data-cy="win"
              :class="{ valid: !$v.leagueBoard.win.$invalid, invalid: $v.leagueBoard.win.$invalid }"
              v-model.number="$v.leagueBoard.win.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.leagueBoard.lose')" for="league-board-lose">Lose</label>
            <input
              type="number"
              class="form-control"
              name="lose"
              id="league-board-lose"
              data-cy="lose"
              :class="{ valid: !$v.leagueBoard.lose.$invalid, invalid: $v.leagueBoard.lose.$invalid }"
              v-model.number="$v.leagueBoard.lose.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.leagueBoard.draw')" for="league-board-draw">Draw</label>
            <input
              type="number"
              class="form-control"
              name="draw"
              id="league-board-draw"
              data-cy="draw"
              :class="{ valid: !$v.leagueBoard.draw.$invalid, invalid: $v.leagueBoard.draw.$invalid }"
              v-model.number="$v.leagueBoard.draw.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.leagueBoard.goalDifference')" for="league-board-goalDifference"
              >Goal Difference</label
            >
            <input
              type="number"
              class="form-control"
              name="goalDifference"
              id="league-board-goalDifference"
              data-cy="goalDifference"
              :class="{ valid: !$v.leagueBoard.goalDifference.$invalid, invalid: $v.leagueBoard.goalDifference.$invalid }"
              v-model.number="$v.leagueBoard.goalDifference.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.leagueBoard.points')" for="league-board-points">Points</label>
            <input
              type="number"
              class="form-control"
              name="points"
              id="league-board-points"
              data-cy="points"
              :class="{ valid: !$v.leagueBoard.points.$invalid, invalid: $v.leagueBoard.points.$invalid }"
              v-model.number="$v.leagueBoard.points.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.leagueBoard.team')" for="league-board-team">Team</label>
            <select class="form-control" id="league-board-team" data-cy="team" name="team" v-model="leagueBoard.team">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="leagueBoard.team && teamOption.id === leagueBoard.team.id ? leagueBoard.team : teamOption"
                v-for="teamOption in teams"
                :key="teamOption.id"
              >
                {{ teamOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.leagueBoard.league')" for="league-board-league">League</label>
            <select class="form-control" id="league-board-league" data-cy="league" name="league" v-model="leagueBoard.league">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="leagueBoard.league && leagueOption.id === leagueBoard.league.id ? leagueBoard.league : leagueOption"
                v-for="leagueOption in leagues"
                :key="leagueOption.id"
              >
                {{ leagueOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.leagueBoard.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./league-board-update.component.ts"></script>
