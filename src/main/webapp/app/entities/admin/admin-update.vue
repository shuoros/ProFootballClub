<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proFootballClubApp.admin.home.createOrEditLabel"
          data-cy="AdminCreateUpdateHeading"
          v-text="$t('proFootballClubApp.admin.home.createOrEditLabel')"
        >
          Create or edit a Admin
        </h2>
        <div>
          <div class="form-group" v-if="admin.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="admin.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.admin.adminId')" for="admin-adminId">Admin Id</label>
            <input
              type="text"
              class="form-control"
              name="adminId"
              id="admin-adminId"
              data-cy="adminId"
              :class="{ valid: !$v.admin.adminId.$invalid, invalid: $v.admin.adminId.$invalid }"
              v-model="$v.admin.adminId.$model"
              required
            />
            <div v-if="$v.admin.adminId.$anyDirty && $v.admin.adminId.$invalid">
              <small class="form-text text-danger" v-if="!$v.admin.adminId.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.admin.firstName')" for="admin-firstName">First Name</label>
            <input
              type="text"
              class="form-control"
              name="firstName"
              id="admin-firstName"
              data-cy="firstName"
              :class="{ valid: !$v.admin.firstName.$invalid, invalid: $v.admin.firstName.$invalid }"
              v-model="$v.admin.firstName.$model"
              required
            />
            <div v-if="$v.admin.firstName.$anyDirty && $v.admin.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.admin.firstName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.admin.lastName')" for="admin-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="admin-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.admin.lastName.$invalid, invalid: $v.admin.lastName.$invalid }"
              v-model="$v.admin.lastName.$model"
              required
            />
            <div v-if="$v.admin.lastName.$anyDirty && $v.admin.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.admin.lastName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proFootballClubApp.admin.user')" for="admin-user">User</label>
            <select class="form-control" id="admin-user" data-cy="user" name="user" v-model="admin.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="admin.user && userOption.id === admin.user.id ? admin.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.id }}
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
            :disabled="$v.admin.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./admin-update.component.ts"></script>
